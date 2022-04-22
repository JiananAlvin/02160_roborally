package gui.view.widgets;


import model.game.Player;
import model.game.board.map.element.Robot;
import model.game.card.Card;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;

/*
 * The whole GamePanel is like this
 * _______________________
 * | map panel      |user1|
 * |                |_____|
 * |                |user2|
 * |                |.....|
 * |________________|_____|
 * |Programming Mat |Log  |
 * |________________|_____|
 * <p>
 * ParticipantInfoPanel is the part of :
 * _______
 * |user1|
 * |_____|
 * or
 * _______
 * |user2|
 * |_____|
 */
public class ParticipantInfoPanel extends JPanel {
    private JLabel[] registers;
    private JLabel labelTokenNumber;
    private JLabel labelLive;
    private JLabel labelUserName;
    private JLabel labelRobotName;
    private JLabel robotImage;

    public ParticipantInfoPanel(Player participant) {
        super(true);
        this.setLayout(null);

        Icon robotIcon = new ImageIcon(new ImageIcon("src/main/resources/images/robots/" + participant.getRobot().getName() + ".jpg").getImage().getScaledInstance(105, 142, Image.SCALE_DEFAULT));
        this.robotImage = new JLabel();
        this.robotImage.setIcon(robotIcon);
        this.robotImage.setBorder(BorderFactory.createLineBorder(Color.gray));
        this.registers = new JLabel[5];
        this.labelTokenNumber = new JLabel("Token:0");
        this.labelLive = new JLabel("Live:5");
        this.labelUserName = new JLabel(participant.getName());
        this.labelRobotName = new JLabel(participant.getRobot().getName().split("_")[0]);


        this.robotImage.setBounds(0, 0, 100, 100);
        this.add(this.robotImage);
        this.labelUserName.setBounds(100, 0, 75, 20);
        this.add(this.labelUserName);
        this.labelRobotName.setBounds(175, 0, 75, 20);
        this.add(this.labelRobotName);
        this.labelTokenNumber.setBounds(250, 0, 75, 20);
        this.add(this.labelTokenNumber);
        this.labelLive.setBounds(325, 0, 75, 20);
        this.add(this.labelLive);


        for (int i = 0; i < this.registers.length; i++) {
            Card registerContent;
            try {
                registerContent = participant.getRegisterArea().getRegisters().get(i);
                this.registers[i] = new JLabel(registerContent.getClass().getSimpleName());
            } catch (IndexOutOfBoundsException e) {
                this.registers[i] = new JLabel("NaN");
            }

            this.registers[i].setBounds(100 + 60 * i, 20, 75, 75);
            this.add(registers[i]);
        }
        this.setBorder(BorderFactory.createLineBorder(Color.gray));
        this.setPreferredSize(new Dimension(400, 100));
    }

//    public static void main(String[] args) {
//        //Schedule a job for the event-dispatching thread:
//        //creating and showing this application's GUI.
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                JFrame frame = new JFrame("GridBagLayoutDemo");
//                //Set up the content pane.
//                frame.add(new ParticipantInfoPanel(new Player("Wenjie", new Robot("TRUNDLE_BOT"))));
//                //Display the window.
//                frame.pack();
//                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//                frame.setSize(frame.getPreferredSize());
//                frame.setVisible(true);
//            }
//        });
//    }
}
