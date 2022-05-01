package gui.room;

import controller.game.GameManager;
import gui.login.LoginPanel;
import gui.waiting.WaitingPanel;
import controller.server.RoomController;
import controller.server.UserController;
import org.json.JSONObject;

import javax.swing.*;

public class RoomPanel extends JPanel {

    private final JComboBox<String> jcbMapName;
    private final JTextField roomNumber;

    public RoomPanel(String userName) {
        JLabel lblMapName = new JLabel("Map");
        String[] mapNameList = {"STARTER", "BEGINNER", "INTERMEDIATE", "ADVANCED"};
        this.jcbMapName = new JComboBox<>(mapNameList);
        JToggleButton btCreateRoom = new JToggleButton("Create room");
        JLabel lblRoomNumber = new JLabel("Room number");
        this.roomNumber = new JTextField();
        JToggleButton btJoinRoom = new JToggleButton("Join room");
        JToggleButton btBack = new JToggleButton("Back");
        this.setLayout(null);
        lblMapName.setBounds(200, 50, 70, 20);
        this.jcbMapName.setBounds(200, 78, 190, 28);
        btCreateRoom.setBounds(450, 78, 150, 30);
        lblRoomNumber.setBounds(200, 200, 100, 20);
        this.roomNumber.setBounds(200, 228, 190, 28);
        btJoinRoom.setBounds(450, 228, 150, 30);
        btBack.setBounds(620, 228, 150, 30);
        this.add(lblMapName);
        this.add(this.jcbMapName);
        this.add(btCreateRoom);
        this.add(lblRoomNumber);
        this.add(this.roomNumber);
        this.add(btJoinRoom);
        this.add(btBack);

        // adding listeners for "Create room" and "Join room" buttons
        btCreateRoom.addActionListener(e -> {
            GameManager.getInstance().processCreateRoom(userName, this.jcbMapName.getSelectedItem() + "");
        });

        btJoinRoom.addActionListener(e -> {
            GameManager.getInstance().processJoinRoom(userName, this.roomNumber.getText());
        });

        btBack.addActionListener(e -> {
            GameManager.getInstance().backToLoginPanle(userName);
        });
    }
}
