package gui.waiting;

import content.MapNameEnum;
import content.RobotNameEnum;
import gui.game.GamePanel;
import gui.room.RoomPanel;
import lombok.SneakyThrows;
import model.Game;
import model.Room;
import model.game.Player;
import model.game.board.map.GameMap;
import model.game.board.map.element.Robot;
import org.json.JSONArray;
import org.json.JSONObject;
import server.controller.RobotController;
import server.controller.RoomController;
import server.controller.UserController;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.Objects;

import static java.lang.Integer.parseInt;
import static java.lang.Integer.parseUnsignedInt;

public class WaitingPanel extends JPanel {

    private JLabel lblRoomNumber;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;
    private JTable tabParticipants;
    private DefaultTableCellRenderer centerRenderer;
    private JToggleButton btStart;
    private JToggleButton btQuit;
    private JLabel lblTip;
    private Timer timer;


    public WaitingPanel(String roomNumberStr, String signal, JFrame frame, String userName) {
        // showing different UI to room owner and participants, only room owner can start the game
        if (Objects.equals(signal, "owner")) {
            this.lblRoomNumber = new JLabel("Room Number: " + roomNumberStr);
            this.lblRoomNumber.setFont(new Font("Calibri", Font.BOLD, 20));
            this.btStart = new JToggleButton("Start");
            this.btQuit = new JToggleButton("Quit");
            loadParticipantsTable(userName, roomNumberStr, frame, signal);
            this.setLayout(null);
            this.lblRoomNumber.setBounds(100, 28, 780, 20);
            this.btStart.setBounds(100, 270, 80, 30);
            this.btQuit.setBounds(300, 270, 80, 30);
            this.add(this.lblRoomNumber);
            this.add(this.btStart);
            this.add(this.btQuit);
            this.btStart.addActionListener(new ActionListener() {
                @SneakyThrows
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    int roomNumber = parseInt(roomNumberStr);
                    JSONObject roomInfoResponse = new RoomController().roomInfo(roomNumber);
                    loadGamePanel(roomNumber, roomInfoResponse, userName, frame, signal);
                }
            });
            this.btQuit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    // delete room and return to the RoomPanel
                    RoomController roomController = new RoomController();
                    roomController.deleteRoom(parseInt(roomNumberStr));

                }
            });
        } else {
            this.lblRoomNumber = new JLabel("Room Number: " + roomNumberStr);
            this.lblRoomNumber.setFont(new Font("Calibri", Font.BOLD, 20));
            this.btQuit = new JToggleButton("Quit");
            this.lblTip = new JLabel("Please wait for the owner to start the game.");
            this.lblTip.setFont(new Font("Calibri", Font.PLAIN, 15));
            this.loadParticipantsTable(userName, roomNumberStr, frame, signal);
            this.setLayout(null);
            this.lblRoomNumber.setBounds(100, 28, 780, 20);
            this.btQuit.setBounds(100, 270, 80, 30);
            this.lblTip.setBounds(300, 275, 580, 30);
            this.add(this.lblRoomNumber);
            this.add(this.btQuit);
            this.add(this.lblTip);
            this.btQuit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    // exit room and return to the RoomPanel
                    UserController userController = new UserController();
                    userController.exitRoom(userName);
                    frame.getContentPane().removeAll();
                    frame.getContentPane().add(new RoomPanel(userName, frame));
                    frame.setVisible(true);
                }
            });
        }
    }


    private void loadParticipantsTable(String userName, String roomNumberStr, JFrame frame, String signal) {
        /*
         * SwingWorker is used to perform the lengthy task of fetching room information from server in a background thread
         * and provide updates to the UI when done
         */
        SwingWorker<JSONObject, Void> updateParticipantsWorker = new SwingWorker<>() {
                    private long timeStamp;
                    private int roomNumber;
                    private RoomController roomController;

                    @Override
                    protected JSONObject doInBackground() throws Exception {
                        // getting the room information from server
                        this.roomNumber = parseInt(roomNumberStr);
                        this.roomController = new RoomController();
                        return this.roomController.roomInfo(roomNumber);
                    }

                    @SneakyThrows
                    @Override
                    protected void done() {
                        JSONObject roomInfoResponse = get();
                        System.out.println(roomInfoResponse);
                        RobotController robotController = new RobotController();

                        // If room is not found, return to the RoomPanel.
                        if (roomInfoResponse.get("status").equals(404)) {
                            timer.stop();
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    frame.getContentPane().removeAll();
                                    frame.getContentPane().add(new RoomPanel(userName, frame));
                                    frame.setVisible(true);
                                }
                            });
                            return;
                        }

                        // If the game started by the owner, initialize GamePanel for participants excluding room owner.
                        if ((!signal.equals("owner")) && roomInfoResponse.get(RoomController.RESPONSE_ROOM_STATUS).equals(RoomController.ROOM_STATUS_START)) {
                            loadGamePanel(this.roomNumber, roomInfoResponse, userName, frame, signal);
                        }

                        /*
                         * If the room information is obtained successfully.
                         * initializing the table of participants
                         */
                        String[][] data = {};
                        String[] columnNames = {"Username", "Robot", "Identity"};
                        tableModel = new DefaultTableModel(data, columnNames) {
                            // making a JTable non-editable
                            @Override
                            public boolean isCellEditable(int row, int column) {
                                //all cells false
                                return false;
                            }
                        };

                        // updating the table of participants
                        long timeStampResponse = roomInfoResponse.getLong(RoomController.RESPONSE_REQUEST_TIME);
                        if (timeStampResponse > this.timeStamp) {
                            // updating the stored timeStamp
                            this.timeStamp = timeStampResponse;
                            // requesting participants in the room then checking everyone's identity owner/participant
                            JSONArray users = (JSONArray) roomInfoResponse.get(RoomController.RESPONSE_USERS_IN_ROOM);
                            for (Object user : users) {
                                if (Objects.equals(user.toString(), this.roomController.roomInfo(this.roomNumber).getString(RoomController.RESPONSE_ROOM_OWNER))) {
                                    tableModel.addRow(new Object[]{user.toString(), robotController.getRobotInfo(user.toString()).getString(RobotController.RESPONSE_ROBOT_NAME), "owner"});
                                } else {
                                    tableModel.addRow(new Object[]{user.toString(), robotController.getRobotInfo(user.toString()).getString(RobotController.RESPONSE_ROBOT_NAME), "participant"});
                                }
                            }

                            tabParticipants = new JTable(tableModel);
                            // centering all columns
                            centerRenderer = new DefaultTableCellRenderer();
                            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                            tabParticipants.setDefaultRenderer(Object.class, centerRenderer);
                            // putting the JTable "tabParticipants" inside a JScrollPane to show the TableHeader
                            scrollPane = new JScrollPane(tabParticipants);
                            scrollPane.setBounds(100, 78, 680, 142);
                            add(scrollPane);
                        }
                    }
                };

                // fetching room information from server every 0.3 seconds
                timer = new Timer(300, new ActionListener() {
                    @SneakyThrows
                    @Override
                    public void actionPerformed(ActionEvent e) {
                        updateParticipantsWorker.execute();
                    }
                });
                timer.start();
    }

    // initializing game and then loading GamePanel
    private void loadGamePanel(int roomNumber, JSONObject roomInfoResponse, String userName, JFrame frame, String signal) throws IOException {
        timer.stop();
        if (signal.equals("owner")) {
            new RoomController().updateStatus(roomNumber, "START");
        }
        Game game = new Game();
        String mapName = roomInfoResponse.getString(RoomController.RESPONSE_MAP_NAME);
        String robotName = (String) new RobotController().getRobotInfo(userName).get(RobotController.RESPONSE_ROBOT_NAME);
        game.init(new Player(userName, new Robot(RobotNameEnum.valueOf(robotName))), new Room(roomNumber), new GameMap(MapNameEnum.valueOf(mapName)), roomInfoResponse);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GamePanel.init(frame, game);
            }
        });
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame(Application.APP_TITLE);
//        frame.setSize(880, 400);
//        WaitingPanel waitingPanel = new WaitingPanel("Jianannnnnn", "100", "participant");
//        frame.add(waitingPanel);
//        frame.setVisible(true);
//        JSONArray exampleArray = new JSONArray();
//        exampleArray.put("Geeks ");
//        exampleArray.put("For ");
//        exampleArray.put("Geeks ");
//        System.out.println(exampleArray.get(1).getClass()); // String
//        for (Object user : users) {...}
//    }
}
