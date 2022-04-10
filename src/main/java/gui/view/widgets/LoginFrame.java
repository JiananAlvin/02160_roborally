package gui.view.widgets;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

public class LoginPanel {
    private final ImageIcon cover = new ImageIcon(new ImageIcon("src/main/resources/images/robots/cover.jpg").getImage().getScaledInstance(900, 400, Image.SCALE_DEFAULT));
    private final JLabel lblCover = new JLabel(cover);
    private final JFrame frame = new JFrame("Roborally Group10 v1.0 - login");
    private final JTextField playerName = new JTextField();
    private final JLabel lblPlayerName = new JLabel("Player name");
    private final JLabel lblRobot = new JLabel("Robot");
    private final JLabel lblChosenRobot = new JLabel();
    private final Icon iconSquashBot = new ImageIcon(new ImageIcon("src/main/resources/images/robots/SQUASH BOT.jpg").getImage().getScaledInstance(105, 142, Image.SCALE_DEFAULT));
    private final JToggleButton btSquashBot = new JToggleButton("", this.iconSquashBot);
    private final Icon iconZoomBot = new ImageIcon(new ImageIcon("src/main/resources/images/robots/ZOOM BOT.jpg").getImage().getScaledInstance(105, 142, Image.SCALE_DEFAULT));
    private final JToggleButton btZoomBot = new JToggleButton("", this.iconZoomBot);
    private final Icon iconHammerBot = new ImageIcon(new ImageIcon("src/main/resources/images/robots/HAMMER BOT.jpg").getImage().getScaledInstance(105, 142, Image.SCALE_DEFAULT));
    private final JToggleButton btHammerBot = new JToggleButton("", this.iconHammerBot);
    private final Icon iconSpinBot = new ImageIcon(new ImageIcon("src/main/resources/images/robots/SPIN BOT.jpg").getImage().getScaledInstance(105, 142, Image.SCALE_DEFAULT));
    private final JToggleButton btSpinBot = new JToggleButton("", this.iconSpinBot);
    private final Icon iconHulkX90 = new ImageIcon(new ImageIcon("src/main/resources/images/robots/HULK X90.jpg").getImage().getScaledInstance(105, 142, Image.SCALE_DEFAULT));
    private final JToggleButton btHulkX90 = new JToggleButton("", this.iconHulkX90);
    private final Icon iconTrundleBot = new ImageIcon(new ImageIcon("src/main/resources/images/robots/TRUNDLE BOT.jpg").getImage().getScaledInstance(105, 142, Image.SCALE_DEFAULT));
    private final JToggleButton btTrundleBot = new JToggleButton("", this.iconTrundleBot);
    private final JToggleButton btOk = new JToggleButton("Login");
    private final JToggleButton btCancel = new JToggleButton("Cancel");

    public void loadLoginPanel() throws InterruptedException {
        this.frame.setSize(900, 400);
        // display cover 2 seconds
        frame.add(lblCover);
        frame.setVisible(true);
        TimeUnit.SECONDS.sleep(2);
        lblCover.setVisible(false);

        // display login interface
        frame.setLayout(null);
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
        frame.add(this.playerName);
        frame.add(this.lblPlayerName);
        frame.add(this.lblRobot);
        frame.add(this.lblChosenRobot);
        frame.add(this.btSquashBot);
        frame.add(this.btZoomBot);
        frame.add(this.btHammerBot);
        frame.add(this.btSpinBot);
        frame.add(this.btHulkX90);
        frame.add(this.btTrundleBot);
        frame.add(this.btOk);
        frame.add(this.btCancel);

        // Add listeners for "Login" and "Cancel" buttons
        btOk.addActionListener(e -> {
            // fetching the player name from the JTextField playerName and the chosen robot when the button is pressed
            String playerNameText = playerName.getText();
            String chosenRobotText = lblChosenRobot.getText();
            System.out.println(playerNameText);
            System.out.println(chosenRobotText);
            frame.setVisible(false);
        });

        btCancel.addActionListener(e -> {
            frame.dispose();
        });

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
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

//    public static void main(String[] args) throws InterruptedException {
//        LoginPanel loginPanel = new LoginPanel();
//        loginPanel.loadLoginPanel();
//    }
}



