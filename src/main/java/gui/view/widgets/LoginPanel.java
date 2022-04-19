package gui.view.widgets;

import content.Application;
import server.controller.user.UserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class LoginPanel extends JPanel {

    private final JTextField userName;
    private final JLabel lblChosenRobot;
    private final JToggleButton btSquashBot;
    private final JToggleButton btZoomBot;
    private final JToggleButton btHammerBot;
    private final JToggleButton btSpinBot;
    private final JToggleButton btHulkX90;
    private final JToggleButton btTrundleBot;

    public LoginPanel(JFrame frame) {
        this.userName = new JTextField();
        JLabel lbluserName = new JLabel("Player name");
        JLabel lblRobot = new JLabel("Robot");
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
        JToggleButton btOk = new JToggleButton("Login");
        JToggleButton btCancel = new JToggleButton("Cancel");

        // Displaying the login interface
        this.setLayout(null);
        RobotListener robotListener = new RobotListener();
        lbluserName.setBounds(100, 8, 70, 20);
        this.userName.setBounds(100, 36, 193, 28);
        lblRobot.setBounds(100, 75, 70, 20);
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
        btOk.setBounds(100, 270, 80, 30);
        btCancel.setBounds(300, 270, 80, 30);
        this.add(this.userName);
        this.add(lbluserName);
        this.add(lblRobot);
        this.add(this.lblChosenRobot);
        this.add(this.btSquashBot);
        this.add(this.btZoomBot);
        this.add(this.btHammerBot);
        this.add(this.btSpinBot);
        this.add(this.btHulkX90);
        this.add(this.btTrundleBot);
        this.add(btOk);
        this.add(btCancel);

        // Adding listeners for "Login" and "Cancel" buttons
        btOk.addActionListener(e -> {
            /*
            fetching the username from the JTextField userName and the chosen robot when the "Login" button is pressed
            & inserting the username and the chosen robot's name into database through API
            & loading the RoomPanel
             */
            UserController userController = new UserController();
            userController.createUser(userName.getText());
            userController.chooseRobot(userName.getText(), lblChosenRobot.getText().replace(' ', '_'));
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new RoomPanel(userName.getText(), frame));
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
//        JFrame frame = new JFrame(Application.APP_TITLE);
//        frame.setSize(880, 400);
//        LoginPanel loginPanel = new LoginPanel(frame);
//        frame.add(loginPanel);
//        frame.setVisible(true);
//    }
}



