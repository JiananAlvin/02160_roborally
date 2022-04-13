package gui.view.widgets;

import model.Room;
import server.controller.robot.RobotController;
import server.controller.room.RoomController;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.Objects;

public class WaitingPanel extends JPanel {

    private int roomNumber;
    private JLabel lblRoomNumber;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;
    private JTable tabParticipants;
    private DefaultTableCellRenderer centerRenderer;
    private JToggleButton btStart;
    private JToggleButton btQuit;
    private JLabel lblTip;

    public WaitingPanel(String userName, String roomNumber) {
        this.btStart = new JToggleButton("Start");
        this.btQuit = new JToggleButton("Quit");

        this.initParticipantsTable(userName, roomNumber);
        this.setLayout(null);
        this.btStart.setBounds(100, 270, 80, 30);
        this.btQuit.setBounds(300, 270, 80, 30);
        this.add(this.btStart);
        this.add(this.btQuit);
    }

    public WaitingPanel(String userName, int roomNumber) {
        this.btQuit = new JToggleButton("Quit");
        this.lblTip = new JLabel("Please wait for the owner to start the game.");
        this.lblTip.setFont(new Font("Calibri", Font.PLAIN, 15));

        this.initParticipantsTable(userName, roomNumber+"");
        this.setLayout(null);
        this.btQuit.setBounds(100, 270, 80, 30);
        this.lblTip.setBounds(300, 275, 580, 30);
        this.add(this.btQuit);
        this.add(this.lblTip);
    }

    public void initParticipantsTable(String userName, String roomNumber) {
        this.lblRoomNumber = new JLabel("Room Number: " + roomNumber);
        this.lblRoomNumber.setFont(new Font("Calibri", Font.BOLD, 20));

        RobotController robotController = new RobotController();
        RoomController roomController = new RoomController();
        String[][] data = new String[][]{{userName, "HARMMER BOT", "owner"}};
//        String[][] data;
//        if (Objects.equals(userName, roomController.roomInfo(Integer.parseInt(roomNumber)).getString("owner"))) {
//            data = new String[][]{
//                    {userName, robotController.getRobotInfo(userName).getString("name"), "owner"},
//            };
//        } else {
//            data = new String[][]{
//                    {userName, robotController.getRobotInfo(userName).getString("name"), "Participant"}
//            };
//        }

        String[] columnNames = {"Username", "Robot", "Identity"};
        this.tableModel = new DefaultTableModel(data, columnNames) {
            // making a JTable non-editable
            @Override
            public boolean isCellEditable(int row, int column) {
                //all cells false
                return false;
            }
        };
        this.tabParticipants = new JTable(this.tableModel);
        // centering all columns
        this.centerRenderer = new DefaultTableCellRenderer();
        centerRenderer.setHorizontalAlignment( JLabel.CENTER );
        this.tabParticipants.setDefaultRenderer(Object.class, centerRenderer);
        // Putting the JTable "tabParticipants" inside a JScrollPane to show the TableHeader
        this.scrollPane = new JScrollPane(tabParticipants);

        this.lblRoomNumber.setBounds(100, 28, 780, 20);
        this.scrollPane.setBounds(100, 78, 680, 142);
        this.add(this.lblRoomNumber);
        this.add(scrollPane);
    }


    public static void main(String[] args) {
        JFrame frame = new JFrame("RoboRally Group10 v1.0");
        frame.setSize(880, 400);
        WaitingPanel waitingPanel = new WaitingPanel("Jianannnnnn", 100);
        frame.add(waitingPanel);
        frame.setVisible(true);
    }
}
