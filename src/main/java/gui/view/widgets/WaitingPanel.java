package gui.view.widgets;

import lombok.EqualsAndHashCode;
import model.Room;
import org.json.JSONArray;
import server.controller.robot.RobotController;
import server.controller.room.RoomController;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;

public class WaitingPanel extends JPanel {

    private JLabel lblRoomNumber;
    private JScrollPane scrollPane;
    private DefaultTableModel tableModel;
    private JTable tabParticipants;
    private DefaultTableCellRenderer centerRenderer;
    private JToggleButton btStart;
    private JToggleButton btQuit;
    private JLabel lblTip;

    public WaitingPanel(String roomNumberStr, String signal, JFrame frame) {
        if (Objects.equals(signal, "owner")) {
            this.lblRoomNumber = new JLabel("Room Number: " + roomNumberStr);
            this.lblRoomNumber.setFont(new Font("Calibri", Font.BOLD, 20));
            this.btStart = new JToggleButton("Start");
            this.btQuit = new JToggleButton("Quit");
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    loadParticipantsTable(roomNumberStr);
                }
            });
            this.setLayout(null);
            this.lblRoomNumber.setBounds(100, 28, 780, 20);
            this.btStart.setBounds(100, 270, 80, 30);
            this.btQuit.setBounds(300, 270, 80, 30);
            this.add(this.lblRoomNumber);
            this.add(this.btStart);
            this.add(this.btQuit);

            this.btStart.addActionListener(new ActionListener() {

                @Override
                public void actionPerformed(ActionEvent arg0) {
                    frame.dispose();
                }
            });

        } else {
            this.lblRoomNumber = new JLabel("Room Number: " + roomNumberStr);
            this.lblRoomNumber.setFont(new Font("Calibri", Font.BOLD, 20));
            this.btQuit = new JToggleButton("Quit");
            this.lblTip = new JLabel("Please wait for the owner to start the game.");
            this.lblTip.setFont(new Font("Calibri", Font.PLAIN, 15));
            this.loadParticipantsTable(roomNumberStr);
            this.setLayout(null);
            this.lblRoomNumber.setBounds(100, 28, 780, 20);
            this.btQuit.setBounds(100, 270, 80, 30);
            this.lblTip.setBounds(300, 275, 580, 30);
            this.add(this.lblRoomNumber);
            this.add(this.btQuit);
            this.add(this.lblTip);
        }

    }


    private void loadParticipantsTable(String roomNumberStr) {
        Timer timer = new Timer(300, new ActionListener() {

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
                int roomNumber = Integer.parseInt(roomNumberStr);
                JSONArray users = (JSONArray) roomController.roomInfo(roomNumber).get("users");
                for (Object user : users) {
                    if (Objects.equals(user.toString(), roomController.roomInfo(roomNumber).getString("owner"))) {
                        tableModel.addRow(new Object[]{user.toString(), robotController.getRobotInfo(user.toString()).getString("name"), "owner"});
                    } else {
                        tableModel.addRow(new Object[]{user.toString(), robotController.getRobotInfo(user.toString()).getString("name"), "participant"});
                    }
                }

                tabParticipants = new JTable(tableModel);
                // centering all columns
                centerRenderer = new DefaultTableCellRenderer();
                centerRenderer.setHorizontalAlignment( JLabel.CENTER );
                tabParticipants.setDefaultRenderer(Object.class, centerRenderer);
                // putting the JTable "tabParticipants" inside a JScrollPane to show the TableHeader
                scrollPane = new JScrollPane(tabParticipants);
                scrollPane.setBounds(100, 78, 680, 142);
                add(scrollPane);
            }
        });
        timer.start();
    }


    public static void main(String[] args) {
//        JFrame frame = new JFrame("RoboRally Group10 v1.0");
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
    }
}
