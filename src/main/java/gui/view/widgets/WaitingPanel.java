package gui.view.widgets;

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
import java.util.Objects;

import static java.lang.Integer.parseInt;

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
        if (Objects.equals(signal, "owner")) {
            this.lblRoomNumber = new JLabel("Room Number: " + roomNumberStr);
            this.lblRoomNumber.setFont(new Font("Calibri", Font.BOLD, 20));
            this.btStart = new JToggleButton("Start");
            this.btQuit = new JToggleButton("Quit");
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    loadParticipantsTable(roomNumberStr, frame, userName);
                }
            });
            this.setLayout(null);
            this.lblRoomNumber.setBounds(100, 28, 780, 20);
            this.btStart.setBounds(100, 270, 80, 30);
            this.btQuit.setBounds(300, 270, 80, 30);
            this.add(this.lblRoomNumber);
            this.add(this.btStart);
            this.add(this.btQuit);

            this.btQuit.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent arg0) {
                    // delete room and return to the RoomPanel
                    RoomController roomController = new RoomController();
                    roomController.deleteRoom(parseInt(roomNumberStr));
                }
            });

            this.btStart.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {

                }

            });

        } else {
            this.lblRoomNumber = new JLabel("Room Number: " + roomNumberStr);
            this.lblRoomNumber.setFont(new Font("Calibri", Font.BOLD, 20));
            this.btQuit = new JToggleButton("Quit");
            this.lblTip = new JLabel("Please wait for the owner to start the game.");
            this.lblTip.setFont(new Font("Calibri", Font.PLAIN, 15));
            this.loadParticipantsTable(roomNumberStr, frame, userName);
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


    private void loadParticipantsTable(String roomNumberStr, JFrame frame, String userName) {

        timer = new Timer(300, new ActionListener() {
            long timeStamp;

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
//TODO: Jianan
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
        });
        timer.start();
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
//    }ze
}
