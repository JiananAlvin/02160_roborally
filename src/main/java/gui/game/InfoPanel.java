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

    private final ArrayList<ParticipantInfoPanel> participantsPanels;
    private final TextArea logArea;

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
        JLabel lblRules = new JLabel("Instructions");
        lblRules.setFont(new Font("Calibri", Font.BOLD, 20));
        TextArea taInstructions = new TextArea("There are 9 cards in front of you.\n" +
                "Look at the position of your robot\n" +
                "and think fast about its moves for\n" +
                "the incoming activation phase. You\n" +
                "have only 30 seconds to sort the\n " +
                "following 9 cards by dragging and\n" +
                "dropping card headers. The cards\n" +
                "placed in the first 5 Registers\n" +
                "(green) will act on your robot in\n" +
                " order. The remaining 4 cards\n" +
                "(leaving in red area) will not be\n" +
                "used in this round. Think and act\n" +
                "fast, paying attention on the\n" +
                "remaining time!", 13, 50, 3);
        taInstructions.setBackground(Color.ORANGE);
        taInstructions.setFont(new Font("Calibri", Font.PLAIN, 15));
        this.logArea.setBounds(475, 0, 250, 300);
        lblRules.setBounds(550, 320, 250, 20);
        taInstructions.setBounds(475, 340, 250, 300);
        this.add(this.logArea);
        this.add(lblRules);
        this.add(taInstructions);
        this.add(lblRules);
    }

    public void addLogToLogPanel(String logContent, Player logFrom) {
        if (logFrom == null)
            logContent = "[SystemLog]:" + logContent + "\n\n";
        else
            logContent = "[" + logFrom.getName() + "]:" + logContent + "\n\n";
        this.logArea.append(logContent);
    }

    public void updateParticipantsInfo(int register, Player player) {
        for (ParticipantInfoPanel participantInfoPanel : participantsPanels) {
            if (player.getName().equals(participantInfoPanel.getLabelUserName().getText())) {
                participantInfoPanel.getRegisters()[register].setText(player.getRegisterArea().getRegisters().get(register).getClass().getSimpleName().substring(4));
                participantInfoPanel.getLabelLive().setText(player.getRobot().getLives() + "");
                participantInfoPanel.getLabelTokenNumber().setText(player.getRobot().getCheckpoints().size() + "");
            }
        }
    }

    public void removeCardInfo() {
        for (ParticipantInfoPanel participantInfoPanel : participantsPanels) {
            participantInfoPanel.getRegisters()[0].setText("NaN");
            participantInfoPanel.getRegisters()[1].setText("NaN");
            participantInfoPanel.getRegisters()[2].setText("NaN");
            participantInfoPanel.getRegisters()[3].setText("NaN");
            participantInfoPanel.getRegisters()[4].setText("NaN");
        }
    }
}
