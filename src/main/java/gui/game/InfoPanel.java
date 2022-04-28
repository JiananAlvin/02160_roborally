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
    private JLabel lblRules;
    private TextArea taInstructions;

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
        this.lblRules = new JLabel("Instructions");
        this.lblRules.setFont(new Font("Calibri", Font.BOLD, 20));
        this.taInstructions = new TextArea("There are 9 cards in front of you.\n" +
                "Look at the position of your robot\n" +
                "and think fast about its moves for\n" +
                "the incoming activation phase. You\n" +
                "have only 30 seconds to sort the\n " +
                "following 9 cards by dragging and\n" +
                "dropping card headers. The cards placed\n" +
                "in the first 5 Registers (green) will\n" +
                "act on your robot in order. The\n" +
                "remaining 4 cards (leaving in red area)\n"+
                "will not be used in this round. Think\n"+
                "and act fast, paying attention on the\n" +
                "remaining time!", 13, 50, 3);
        this.taInstructions.setBackground(Color.ORANGE);
        this.taInstructions.setFont(new Font("Calibri", Font.PLAIN, 15));
        this.logArea.setBounds(475, 0, 250, 300);
        this.lblRules.setBounds(550, 320, 250, 20);
        this.taInstructions.setBounds(475, 320, 250, 300);
        this.add(this.logArea);
        this.add(this.lblRules);
        this.add(this.taInstructions);
        this.add(this.lblRules);
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
