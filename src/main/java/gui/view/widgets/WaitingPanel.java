package gui.view.widgets;

import server.controller.room.RoomController;

import javax.swing.*;
import java.awt.*;

public class WaitingPanel extends JPanel {

    private int roomNumber;
    private JLabel lblRoomNumber;
    private JList lsPlayers;
    private JToggleButton btStart;
    private JToggleButton btQuit;

    public WaitingPanel(String userName, String roomNumber) {

        this.lblRoomNumber = new JLabel("Room Number: " + roomNumber);
        this.lblRoomNumber.setFont(new Font("Calibri", Font.BOLD, 20));
        this.btStart = new JToggleButton("Start");
        this.btQuit = new JToggleButton("Quit");
        this.setLayout(null);
        this.lblRoomNumber.setBounds(100, 8, 780, 20);
        this.btStart.setBounds(100, 270, 80, 30);
        this.btQuit.setBounds(300, 270, 80, 30);
        this.add(lblRoomNumber);
        this.add(btStart);
        this.add(btQuit);
    };

    public WaitingPanel(String userName, int roomNumber) {
        lblRoomNumber = new JLabel("Room Number: " + roomNumber);
        lblRoomNumber.setFont(new Font("Calibri", Font.BOLD, 20));
        this.setLayout(null);
        this.setBounds(100, 8, 70, 20);
        this.add(lblRoomNumber);
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("RoboRally Group10 v1.0");
        frame.setSize(880, 400);
        WaitingPanel waitingPanel = new WaitingPanel("Jianan", "001");
        frame.add(waitingPanel);
        frame.setVisible(true);
    }
}
