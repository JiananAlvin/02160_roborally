package gui.view.widgets;

import server.controller.room.RoomController;
import server.controller.user.UserController;

import javax.swing.*;

public class RoomPanel<IntField> extends JPanel{

    private final JComboBox<String> jcbMapName;
    private final JTextField roomNumber;

    public RoomPanel(String userName, JFrame frame) {
        JLabel lblMapName = new JLabel("Map");
        String[] mapNameList = {"STARTER", "BEGINNER", "INTERMEDIATE", "ADVANCED"};
        this.jcbMapName = new JComboBox<>(mapNameList);
        JToggleButton btCreateRoom = new JToggleButton("Create room");
        JLabel lblRoomNumber = new JLabel("Room number");
        this.roomNumber = new JTextField();
        JToggleButton btJionRoom = new JToggleButton("Join room");

        this.setLayout(null);
        lblMapName.setBounds(200, 50, 70, 20);
        this.jcbMapName.setBounds(200, 78, 190, 28);
        btCreateRoom.setBounds(450, 78, 150, 30);
        lblRoomNumber.setBounds(200, 200, 100, 20);
        this.roomNumber.setBounds(200, 228, 190, 28);
        btJionRoom.setBounds(450, 228, 150, 30);
        this.add(lblMapName);
        this.add(this.jcbMapName);
        this.add(btCreateRoom);
        this.add(lblRoomNumber);
        this.add(this.roomNumber);
        this.add(btJionRoom);

        // Add listeners for "Create room" and "Join room" buttons
        btCreateRoom.addActionListener(e -> {
            /*
             fetching the map name when the "Create room" button is pressed
             creating a room for the user through API
             */
            RoomController roomController = new RoomController();
            roomController.createRoom(userName, this.jcbMapName.getSelectedItem().toString());
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new WaitingPanel("Jianannnnnn", "100"));
            frame.setVisible(true);
        });

        btJionRoom.addActionListener(e -> {
            /*
            fetching the room number when the "Join room" button is pressed
            inserting the player info into the room through API
             */
            UserController userController = new UserController();
            userController.joinRoom(userName, Integer.parseInt(this.roomNumber.getText()));
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new WaitingPanel("Jianannnnnn", 100));
            frame.setVisible(true);
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("RoboRally Group10 v1.0");
        frame.setSize(880, 400);
        RoomPanel roomPanel = new RoomPanel("ddd", frame);
        frame.add(roomPanel);
        frame.setVisible(true);
    }
}
