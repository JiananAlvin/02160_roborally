package gui.room;

import gui.login.LoginPanel;
import gui.waiting.WaitingPanel;
import server.controller.RoomController;
import server.controller.UserController;
import org.json.JSONObject;

import javax.swing.*;

public class RoomPanel<IntField> extends JPanel {

    private final JComboBox<String> jcbMapName;
    private final JTextField roomNumber;

    public RoomPanel(String userName, JFrame frame) {
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
            /*
             fetching the map name when the "Create room" button is pressed
             creating a room for the user through API
             */
            RoomController roomController = new RoomController();
            JSONObject response = roomController.createRoom(userName, this.jcbMapName.getSelectedItem().toString());
            String roomNumberStr = response.get("room_number").toString();
            roomController.updateStatus(Integer.parseInt(roomNumberStr), RoomController.ROOM_STATUS_WAITING);
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new WaitingPanel(roomNumberStr, "owner", frame, userName));
            frame.setVisible(true);
        });

        btJoinRoom.addActionListener(e -> {
            /*
            fetching the room number when the "Join room" button is pressed
            inserting the qplayer info into the room through API
             */
            UserController userController = new UserController();
            String roomNumberStr = this.roomNumber.getText();
            userController.joinRoom(userName, Integer.parseInt(roomNumberStr));
            if (userController.getResponse().get("status").equals(200)) {
                frame.getContentPane().removeAll();
                frame.getContentPane().add(new WaitingPanel(roomNumberStr, "participant", frame, userName));
                frame.setVisible(true);
            } else if (userController.getResponse().get("status").equals(400)) {
                JOptionPane.showMessageDialog(frame, "Room does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        btBack.addActionListener(e -> {
            // deleting the user in database and returning to LoginPanel
            UserController userController = new UserController();
            userController.deleteUser(userName);
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new LoginPanel(frame));
            frame.setVisible(true);
        });
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame(Application.APP_TITLE);
//        frame.setSize(880, 400);
//        RoomPanel roomPanel = new RoomPanel("ddd", frame);
//        frame.add(roomPanel);
//        frame.setVisible(true);
//    }
}
