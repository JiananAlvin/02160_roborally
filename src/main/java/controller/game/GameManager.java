package controller.game;

import app.ClientRunner;
import controller.server.RoomController;
import controller.server.UserController;
import gui.cover.CoverPanel;
import gui.login.LoginPanel;
import gui.room.RoomPanel;
import gui.waiting.WaitingPanel;
import org.json.JSONObject;

import javax.swing.*;

public class GameManager {

    private static GameManager INSTANCE;

    private GameManager() {
    }

    public static GameManager getInstance() {
        if (GameManager.INSTANCE == null)
            GameManager.INSTANCE = new GameManager();
        return GameManager.INSTANCE;
    }


    /**
     * When the programming run, create a LoginPanel and show it in frame.
     */
    public void showLoginPanel() {
        ClientRunner clientRunner = ClientRunner.getApplicationInstance();
        clientRunner.getFrame().setSize(880, 400);
        clientRunner.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clientRunner.getFrame().setLocationRelativeTo(null); // this method display the JFrame to center position of a screen
        clientRunner.getFrame().getContentPane().add(new CoverPanel());
        clientRunner.getFrame().setVisible(true);
        Timer timer = new Timer(2000, e -> {
            clientRunner.getFrame().getContentPane().removeAll();
            clientRunner.getFrame().getContentPane().add(new LoginPanel());
            clientRunner.getFrame().setVisible(true);
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void closeJFrame() {
        ClientRunner.getApplicationInstance().getFrame().dispose();
    }

    public void processLogin(String userName, String robotName) {
        /*
            fetching the username from the JTextField userName and the chosen robot when the "Login" button is pressed
            & inserting the username and the chosen robot's name into database through API
            & loading the RoomPanel
             */
        UserController userController = new UserController();
        userController.createUser(userName);
        if (userController.getResponse().get("status").equals(200)) {
            if ("".equals(robotName)) {
                JOptionPane.showMessageDialog(null, "Please choose a robot");
                userController.deleteUser(userName);
            } else {
                userController.chooseRobot(userName, robotName.replace(' ', '_'));
                JFrame frame = ClientRunner.getApplicationInstance().getFrame();
                frame.getContentPane().removeAll();
                frame.getContentPane().add(new RoomPanel(userName));
                frame.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(ClientRunner.getApplicationInstance().getFrame(), "Username already exists");
        }
    }

    public void processCreateRoom(String userName, String mapName) {
        /*
             fetching the map name when the "Create room" button is pressed
             creating a room for the user through API
             */
        RoomController roomController = new RoomController();
        JSONObject response = roomController.createRoom(userName, mapName);
        String roomNumberStr = response.get("room_number").toString();

        JFrame frame = ClientRunner.getApplicationInstance().getFrame();
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new WaitingPanel(roomNumberStr, "owner", frame, userName));
        frame.setVisible(true);
    }

    public void processJoinRoom(String userName, String roomNumber) {
        /*
            fetching the room number when the "Join room" button is pressed
            inserting the qplayer info into the room through API
             */
        UserController userController = new UserController();
        userController.joinRoom(userName, Integer.parseInt(roomNumber));
        JFrame frame = ClientRunner.getApplicationInstance().getFrame();
        if (userController.getResponse().get("status").equals(200)) {
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new WaitingPanel(roomNumber, "participant", frame, userName));
            frame.setVisible(true);
        } else if (userController.getResponse().get("status").equals(400)) {
            JOptionPane.showMessageDialog(frame, "Room does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void backToLoginPanle(String userName) {
        // deleting the user in database and returning to LoginPanel
        UserController userController = new UserController();
        userController.deleteUser(userName);
        JFrame frame = ClientRunner.getApplicationInstance().getFrame();
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new LoginPanel());
        frame.setVisible(true);
    }
}
