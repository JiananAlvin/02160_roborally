package gui.game;

import lombok.Data;
import lombok.EqualsAndHashCode;
import model.game.Player;
import model.game.card.Card;

import javax.swing.*;
import java.awt.*;


/*
 * The whole GamePanel is like this
 * _______________________
 * |    board Panel |user1|
 * |                |_____|
 * |                |user2|
 * |                |-----|
 * |                |.log.|
 * |________________|_____|
 * |MatPanel              |
 * |________________ _____|
 * <p>
 * ParticipantInfoPanel is the part of:
 * _______
 * |user1|
 * |_____|
 * or
 * _______
 * |user2|
 * |_____|
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class ParticipantInfoPanel extends JPanel {
    private JLabel[] registers;
    private JLabel labelTokenNumber;
    private JLabel labelLive;
    private JLabel labelUserName;
    private JLabel labelRobotName;
    private JLabel lblRobot;


    public ParticipantInfoPanel(Player participant) {
        super(true);
        this.setLayout(null);

        Icon iconRobot = new ImageIcon(new ImageIcon("src/main/resources/images/robots/" + participant.getRobot().getName() + ".png").getImage().getScaledInstance(100, 110, Image.SCALE_DEFAULT));
        this.lblRobot = new JLabel();
        this.lblRobot.setIcon(iconRobot);
        this.lblRobot.setBorder(BorderFactory.createLineBorder(Color.gray));
        this.registers = new JLabel[5];
        this.labelTokenNumber = new JLabel("Token:" + participant.getRobot().getCheckpoints().size());
        this.labelLive = new JLabel("Lives:" + participant.getRobot().getLives());
        this.labelUserName = new JLabel(participant.getName());
        this.labelRobotName = new JLabel(participant.getRobot().getName().replace("_", " "));

        this.lblRobot.setBounds(0, 0, 100, 100);
        this.labelUserName.setBounds(115, 0, 75, 20);
        this.labelRobotName.setBounds(240, 0, 90, 20);
        this.labelTokenNumber.setBounds(340, 0, 60, 20);
        this.labelLive.setBounds(410, 0, 60, 20);

        this.add(this.lblRobot);
        this.add(this.labelUserName);
        this.add(this.labelRobotName);
        this.add(this.labelTokenNumber);
        this.add(this.labelLive);


        for (int i = 0; i < this.registers.length; i++) {
            Card registerContent;
            try {
                registerContent = participant.getRegisterArea().getRegisters().get(i);
                this.registers[i] = new JLabel(registerContent.getClass().getSimpleName());
            } catch (IndexOutOfBoundsException e) {
                this.registers[i] = new JLabel("NaN");
            }

            this.registers[i].setBounds(125 + 60 * i, 20, 75, 75);
            this.add(registers[i]);
        }
        this.setBorder(BorderFactory.createLineBorder(Color.gray));
        this.setPreferredSize(new Dimension(500, 100));
    }

}
