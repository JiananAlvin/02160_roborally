package gui.view.widgets;

import server.controller.user.UserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {

    private final JTextField playerName;
    private final JLabel lblPlayerName;
    private final JLabel lblRobot;
    private final JLabel lblChosenRobot;
    private final JToggleButton btSquashBot;
    private final JToggleButton btZoomBot;
    private final JToggleButton btHammerBot;
    private final JToggleButton btSpinBot;
    private final JToggleButton btHulkX90;
    private final JToggleButton btTrundleBot;
    private final JToggleButton btOk;
    private final JToggleButton btCancel;

    public LoginPanel(JFrame frame) {
        this.playerName = new JTextField();
        this.lblPlayerName = new JLabel("Player name");
        this.lblRobot = new JLabel("Robot");
        this.lblChosenRobot = new JLabel();
        Icon iconSquashBot = new ImageIcon(new ImageIcon("src/main/resources/images/robots/SQUASH BOT.jpg").getImage().getScaledInstance(105, 142, Image.SCALE_DEFAULT));
        this.btSquashBot = new JToggleButton("", iconSquashBot);
        Icon iconZoomBot = new ImageIcon(new ImageIcon("src/main/resources/images/robots/ZOOM BOT.jpg").getImage().getScaledInstance(105, 142, Image.SCALE_DEFAULT));
        this.btZoomBot = new JToggleButton("", iconZoomBot);
        Icon iconHammerBot = new ImageIcon(new ImageIcon("src/main/resources/images/robots/HAMMER BOT.jpg").getImage().getScaledInstance(105, 142, Image.SCALE_DEFAULT));
        this.btHammerBot = new JToggleButton("", iconHammerBot);
        Icon iconSpinBot = new ImageIcon(new ImageIcon("src/main/resources/images/robots/SPIN BOT.jpg").getImage().getScaledInstance(105, 142, Image.SCALE_DEFAULT));
        this.btSpinBot = new JToggleButton("", iconSpinBot);
        Icon iconHulkX90 = new ImageIcon(new ImageIcon("src/main/resources/images/robots/HULK X90.jpg").getImage().getScaledInstance(105, 142, Image.SCALE_DEFAULT));
        this.btHulkX90 = new JToggleButton("", iconHulkX90);
        Icon iconTrundleBot = new ImageIcon(new ImageIcon("src/main/resources/images/robots/TRUNDLE BOT.jpg").getImage().getScaledInstance(105, 142, Image.SCALE_DEFAULT));
        this.btTrundleBot = new JToggleButton("", iconTrundleBot);
        this.btOk = new JToggleButton("Login");
        this.btCancel = new JToggleButton("Cancel");

        // display login interface
        this.setLayout(null);
        RobotListener robotListener = new RobotListener();
        this.lblPlayerName.setBounds(100, 8, 70, 20);
        this.playerName.setBounds(100, 36, 193, 28);
        this.lblRobot.setBounds(100, 75, 70, 20);
        this.lblChosenRobot.setBounds(200, 75, 100, 20);
        this.btSquashBot.setBounds(100, 103, 105, 142);
        this.btSquashBot.addActionListener(robotListener);
        this.btZoomBot.setBounds(215, 103, 105, 142);
        this.btZoomBot.addActionListener(robotListener);
        this.btHammerBot.setBounds(330, 103, 105, 142);
        this.btHammerBot.addActionListener(robotListener);
        this.btSpinBot.setBounds(445, 103, 105, 142);
        this.btSpinBot.addActionListener(robotListener);
        this.btHulkX90.setBounds(560, 103, 105, 142);
        this.btHulkX90.addActionListener(robotListener);
        this.btTrundleBot.setBounds(675, 103, 105, 142);
        this.btTrundleBot.addActionListener(robotListener);
        this.btOk.setBounds(100, 270, 80, 30);
        this.btCancel.setBounds(300, 270, 80, 30);
        this.add(this.playerName);
        this.add(this.lblPlayerName);
        this.add(this.lblRobot);
        this.add(this.lblChosenRobot);
        this.add(this.btSquashBot);
        this.add(this.btZoomBot);
        this.add(this.btHammerBot);
        this.add(this.btSpinBot);
        this.add(this.btHulkX90);
        this.add(this.btTrundleBot);
        this.add(this.btOk);
        this.add(this.btCancel);

        // Add listeners for "Login" and "Cancel" buttons
        btOk.addActionListener(e -> {
            // fetching the player name from the JTextField playerName and the chosen robot when the "Login" button is pressed
            // insert the player name and the chosen robot's name into database through API
            UserController userController = new UserController();
            userController.createUser(playerName.getText());
            userController.chooseRobot(playerName.getText(), lblChosenRobot.getText().replace(' ', '_'));
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new RoomPanel(playerName.getText()));
            frame.setVisible(true);
        });

        btCancel.addActionListener(e -> {
            frame.dispose();
        });
    }

    private class RobotListener implements ActionListener {
        public void actionPerformed(ActionEvent e) {
            if (e.getSource() == btSquashBot) {
                lblChosenRobot.setText("SQUASH BOT");
            } else if (e.getSource() == btZoomBot) {
                lblChosenRobot.setText("ZOOM BOT");
            } else if (e.getSource() == btHammerBot) {
                lblChosenRobot.setText("HAMMER BOT");
            } else if (e.getSource() == btSpinBot) {
                lblChosenRobot.setText("SPIN BOT");
            } else if (e.getSource() == btHulkX90) {
                lblChosenRobot.setText("HULK X90");
            } else if (e.getSource() == btTrundleBot) {
                lblChosenRobot.setText("TRUNDLE BOT");
            }
        }
    }

//    public static void main(String[] args) {
//        JFrame frame = new JFrame("RoboRally Group10 v1.0");
//        frame.setSize(880, 400);
//        LoginPanel loginPanel = new LoginPanel();
//        frame.add(loginPanel);
//        frame.setVisible(true);
//    }
}



