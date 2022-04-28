package gui.waiting;

import content.MapNameEnum;
import content.RobotNameEnum;
import gui.game.GamePanel;
import gui.room.RoomPanel;
import lombok.SneakyThrows;
import model.Game;

import model.game.Player;
import model.game.Room;
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

    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;
    private JTable tabParticipants;
    private DefaultTableCellRenderer centerRenderer;
    private Timer timer;

    public WaitingPanel(String roomNumberStr, String signal, JFrame frame, String userName) {
        JToggleButton btQuit;
        JLabel lblRoomNumber;
        if (Objects.equals(signal, "owner")) {
            lblRoomNumber = new JLabel("Room Number: " + roomNumberStr);
            lblRoomNumber.setFont(new Font("Calibri", Font.BOLD, 20));
            JToggleButton btStart = new JToggleButton("Start");
            btQuit = new JToggleButton("Quit");
            loadParticipantsTable(userName, roomNumberStr, frame, signal);

            this.setLayout(null);
            lblRoomNumber.setBounds(100, 28, 780, 20);
            btStart.setBounds(100, 270, 80, 30);
            btQuit.setBounds(300, 270, 80, 30);
            this.add(lblRoomNumber);
            this.add(btStart);
            this.add(btQuit);
            btQuit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    // delete room and return to the RoomPanel
                    RoomController roomController = new RoomController();
                    roomController.deleteRoom(parseInt(roomNumberStr));

                }
            });

            btStart.addActionListener(new ActionListener() {
                @SneakyThrows
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    int roomNumber = parseInt(roomNumberStr);
                    JSONObject roomInfoResponse = new RoomController().roomInfo(roomNumber);
                    startGamePanel(roomNumber, roomInfoResponse, userName, frame, signal);
                }
            });
        } else {
            lblRoomNumber = new JLabel("Room Number: " + roomNumberStr);
            lblRoomNumber.setFont(new Font("Calibri", Font.BOLD, 20));
            btQuit = new JToggleButton("Quit");
            JLabel lblTip = new JLabel("Please wait for the owner to start the game.");
            lblTip.setFont(new Font("Calibri", Font.PLAIN, 15));
            this.loadParticipantsTable(userName, roomNumberStr, frame, signal);
            this.setLayout(null);
            lblRoomNumber.setBounds(100, 28, 780, 20);
            btQuit.setBounds(100, 270, 80, 30);
            lblTip.setBounds(300, 275, 580, 30);
            this.add(lblRoomNumber);
            this.add(btQuit);
            this.add(lblTip);

            btQuit.addActionListener(new ActionListener() {
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

        timer = new Timer(300, new ActionListener() {
            long timeStamp;

            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                // initializing the table of participants
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
                RoomController roomController = new RoomController();
                RobotController robotController = new RobotController();
                int roomNumber = parseInt(roomNumberStr);
                JSONObject roomInfoResponse = roomController.roomInfo(roomNumber);

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


                if ((!signal.equals("owner")) && roomInfoResponse.get(RoomController.RESPONSE_ROOM_STATUS).equals(RoomController.ROOM_STATUS_START)) {
                    startGamePanel(roomNumber, roomInfoResponse, userName, frame, signal);
                }

                long timeStampResponse = roomInfoResponse.getLong(RoomController.RESPONSE_REQUEST_TIME);
                if (timeStampResponse > this.timeStamp) {
                    // update the stored timeStamp
                    this.timeStamp = timeStampResponse;
                    JSONArray users = (JSONArray) roomInfoResponse.get(RoomController.RESPONSE_USERS_IN_ROOM);
                    for (Object user : users) {
                        if (Objects.equals(user.toString(), roomController.roomInfo(roomNumber).getString(RoomController.RESPONSE_ROOM_OWNER))) {
                            tableModel.addRow(new Object[]{user.toString(), robotController.getRobotInfo(user.toString()).getString(RobotController.RESPONSE_ROBOT_NAME), "owner"});
                        } else {
                            tableModel.addRow(new Object[]{user.toString(), robotController.getRobotInfo(user.toString()).getString(RobotController.RESPONSE_ROBOT_NAME), "participant"});
                        }
                    }

                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
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
                    });
                }
            }
        });
        timer.start();
    }

    private void startGamePanel(int roomNumber, JSONObject roomInfoResponse, String userName, JFrame frame, String signal) throws IOException {
        timer.stop();
        if (signal.equals("owner")) {
            new RoomController().updateStatus(roomNumber, "START");
        }
        Game game = new Game();
//                JSONObject roomInfoResponse = new RoomController().roomInfo(Integer.parseInt(roomNumberStr));
        String mapName = roomInfoResponse.getString(RoomController.RESPONSE_MAP_NAME);
//                int roomNumber = roomInfoResponse.getInt(RoomController.RESPONSE_ROOM_NUMBER);
        String robotName = (String) new RobotController().getRobotInfo(userName).get(RobotController.RESPONSE_ROBOT_NAME);
        game.init(userName, new Room(roomNumber), new GameMap(MapNameEnum.valueOf(mapName)), roomInfoResponse);
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                GamePanel.init(frame, game);
            }
        });
    }
}