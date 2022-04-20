package gui.view.widgets;

import content.RobotName;
import model.game.Player;
import model.game.board.map.element.Robot;
import model.game.board.mat.element.RegisterArea;

import javax.swing.*;
import java.awt.*;

public class SimpleProgrammingMatPanel extends JPanel {

    private JTextField[] registers;
    private JLabel labelTokenNumber;
    private JLabel labelLive;
    private JLabel labelUserName;
    private JLabel labelRobotName;

    public SimpleProgrammingMatPanel(Player user) {
        super(true);
        this.setLayout(new GridBagLayout());
        GridBagConstraints c = new GridBagConstraints();
        this.registers = new JTextField[5];
        this.labelTokenNumber = new JLabel("TokenNumber:0");
        this.labelLive = new JLabel("Live:5");
        this.labelUserName = new JLabel("User:" + user.getName());
        this.labelRobotName = new JLabel("Robot:" + user.getRobot().getName());

        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 0;
        c.gridy = 0;
        this.add(labelUserName, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 1;
        c.gridy = 0;
        this.add(labelRobotName, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 2;
        c.gridy = 0;
        this.add(labelTokenNumber, c);
        c.fill = GridBagConstraints.HORIZONTAL;
        c.weightx = 1;
        c.gridx = 3;
        c.gridy = 0;
        this.add(labelLive, c);

        for (int i = 0; i < this.registers.length; i++) {
            this.registers[i] = new JTextField();
            c.fill = GridBagConstraints.HORIZONTAL;
            c.weightx = 1;
            c.gridx = i;
            c.gridy = 1;
            this.add(registers[i], c);
        }
        this.setPreferredSize(new Dimension(1000, 50));
    }


    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame("GridBagLayoutDemo");
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(frame.getPreferredSize());
                //Set up the content pane.
                frame.add(new SimpleProgrammingMatPanel(new Player("Wenjie", new Robot(RobotName.TRUNDLE_BOT))));

                //Display the window.
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
