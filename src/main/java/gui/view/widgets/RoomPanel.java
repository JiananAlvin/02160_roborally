package gui.view.widgets;

import io.cucumber.java.sl.In;
import server.controller.room.RoomController;
import server.controller.user.UserController;

import javax.swing.*;

public class RoomPanel extends JPanel{

    private final JLabel lblMapName;
    JComboBox<String> jcbMapName;
    private final JToggleButton btCreateRoom;
    private final JLabel lblRoomNumber;
    private final JTextField roomNumber;
    private final JToggleButton btJionRoom;

    public RoomPanel(String playerName) {
        this.lblMapName = new JLabel("Map");
        String[] mapNameList = {"STARTER", "BEGINNER", "INTERMEDIATE", "ADVANCED"};
        this.jcbMapName = new JComboBox<>(mapNameList);
        this.btCreateRoom = new JToggleButton("Create room");
        this.lblRoomNumber = new JLabel("Room number");
        this.roomNumber = new JTextField();
        this.btJionRoom = new JToggleButton("Join room");

        this.setLayout(null);
        this.lblMapName.setBounds(200, 50, 70, 20);
        this.jcbMapName.setBounds(200, 78, 190, 28);
        this.btCreateRoom.setBounds(450, 78, 150, 30);
        this.lblRoomNumber.setBounds(200, 200, 100, 20);
        this.roomNumber.setBounds(200, 228, 190, 28);
        this.btJionRoom.setBounds(450, 228, 150, 30);
        this.add(this.lblMapName);
        this.add(this.jcbMapName);
        this.add(this.btCreateRoom);
        this.add(this.lblRoomNumber);
        this.add(this.roomNumber);
        this.add(this.btJionRoom);

        // Add listeners for "Create room" and "Join room" buttons
        btCreateRoom.addActionListener(e -> {
            // fetching the map name when the "Create room" button is pressed
            // help the player create a room through API
            RoomController roomController = new RoomController();
            roomController.createRoom(playerName, this.jcbMapName.getSelectedItem().toString());
            System.out.println(roomController.getResponse());
        });

        btJionRoom.addActionListener(e -> {
            // fetching the room number when the "Join room" button is pressed
            // insert the player info into the room through API
            UserController userController = new UserController();
            userController.joinRoom(playerName, Integer.parseInt(this.roomNumber.getText()));
        });
    }

    public static void main(String[] args) {
        JFrame frame = new JFrame("RoboRally Group10 v1.0");
        frame.setSize(880, 400);
        RoomPanel roomPanel = new RoomPanel("ddd");
        frame.add(roomPanel);
        frame.setVisible(true);
    }
}
