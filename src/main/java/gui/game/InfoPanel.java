package gui.game;

import content.RobotNameEnum;
import model.game.Player;
import model.game.board.map.element.Robot;

import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;

/*
 * The whole GamePanel is like this
 * ________________________
 * |    board Panel |user1|
 * |                |_____|
 * |                |user2|
 * |                |-----|
 * |                |.log.|
 * |________________|_____|
 * |MatPanel              |
 * |________________ _____|
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
    private TextArea logArea;

    public InfoPanel(ArrayList<Player> participants, Player user) {
        super(true);
        this.setLayout(null);

        // displaying other players' information on the right side of the map
        this.participantsPanels = new ArrayList<>();
        int i = 0;
        for (Player participant : participants) {
            if (!participant.getName().equals(user.getName())) {
                ParticipantInfoPanel participantInfoPanel = new ParticipantInfoPanel(participant);
                participantInfoPanel.setBounds(0, i++ * 100, 455, 100);
                this.participantsPanels.add(participantInfoPanel);
                this.add(participantInfoPanel);
            }
        }

        // showing game logs on the right side of the ParticipantInfoPanel
        this.logArea = new TextArea("[System Log]:\n Welcome to this game!\n[System Log]:\n The programming phase of 1st rount starts!", 20, 55, TextArea.SCROLLBARS_VERTICAL_ONLY);
        this.logArea.setEditable(false);
        this.logArea.setBounds(475, 0, 250, 300);
        this.add(this.logArea);
    }

    public void addLogToLogPanel(String logContent, Player logFrom) {
        if (logFrom == null)
            logContent = "[SystemLog]:" + logContent + "\n\n";
        else
            logContent = "[" + logFrom.getName() + "]:" + logContent + "\n\n";
        this.logArea.append(logContent);
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
                participants.add(new Player("Test1", new Robot(RobotNameEnum.HAMMER_BOT)));
                participants.add(new Player("Test2", new Robot(RobotNameEnum.HULK_X90)));
                participants.add(new Player("Test3", new Robot(RobotNameEnum.SPIN_BOT)));
                participants.add(new Player("Test4", new Robot(RobotNameEnum.SQUASH_BOT)));
                participants.add(new Player("Test5", new Robot(RobotNameEnum.TRUNDLE_BOT)));
                participants.add(new Player("Test6", new Robot(RobotNameEnum.ZOOM_BOT)));
                frame.add(new InfoPanel(participants, new Player("Test1", new Robot(RobotNameEnum.HAMMER_BOT))));
                //Display the window.
                frame.pack();
                frame.setVisible(true);
            }
        });
    }
}
