package gui.view.widgets;

import content.RobotName;
import model.game.Player;
import model.game.board.map.element.Robot;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

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
 * InfoPanel is the part of :
 * _______
 * |user1|
 * |_____|
 * |user2|
 * |.....|
 * |_____|
 * |Log  |
 * |_____|
 */


public class InfoPanel extends JPanel {

    private ArrayList<ParticipantInfoPanel> participantsPanels;
    private ScrollPane logPanel;
    private TextArea logArea;

    public InfoPanel(ArrayList<Player> participants) {
        super(true);
        this.participantsPanels = new ArrayList<>();
        int i = 0;
        for (Player participant : participants) {
            ParticipantInfoPanel participantInfoPanel = new ParticipantInfoPanel(participant);
            this.participantsPanels.add(participantInfoPanel);
            participantInfoPanel.setBounds(0, i++ * 100, 400, 100);
            this.add(participantInfoPanel);
        }
        this.logArea = new TextArea("[System Log]:\n Welcome to this game!\n[System Log]:\n The programming phase of 1st rount starts!",20,55,TextArea.SCROLLBARS_VERTICAL_ONLY);
//        this.logPanel = new ScrollPane(this.logArea);
        this.logArea.setBounds(0, i++ * 100, 400, 1000 - i * 100);
        this.logArea.setEditable(false);
        this.add(logArea);
        this.setPreferredSize(new Dimension(400, 1000));
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
                ArrayList<Player> participants = new ArrayList<>();
                participants.add(new Player("Test1", new Robot(RobotName.HAMMER_BOT)));
                participants.add(new Player("Test2", new Robot(RobotName.HULK_X90)));
                participants.add(new Player("Test3", new Robot(RobotName.SPIN_BOT)));
                participants.add(new Player("Test4", new Robot(RobotName.SQUASH_BOT)));
                participants.add(new Player("Test5", new Robot(RobotName.TRUNDLE_BOT)));
                participants.add(new Player("Test6", new Robot(RobotName.ZOOM_BOT)));
                frame.add(new InfoPanel(participants));
                //Display the window.
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
