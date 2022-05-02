package gui.game;

import controller.game.GameController;
import lombok.Data;
import lombok.EqualsAndHashCode;
import model.Game;

import javax.swing.*;
import java.awt.*;

/*
 * The whole GamePanel is like this
 * ________________________
 * |    board Panel |user1|
 * |                |_____|
 * |                |user2|
 * |                |-----|
 * |                |.log.|
 * |________________|_____|
 * |       MatPanel       |
 * |______________________|
 */

@EqualsAndHashCode(callSuper = true)
@Data
public class GamePanel extends JPanel {

    private BoardPanel boardPanel;
    private InfoPanel infoPanel;
    private MatPanel matPanel;
    public static final Color[] userColors = new Color[]{Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.MAGENTA};


    public GamePanel() {
        super(true);
        this.infoPanel = new InfoPanel(Game.getInstance().getParticipants(), Game.getInstance().getUser());
        this.boardPanel = new BoardPanel();
        this.setLayout(null);

        this.boardPanel.setBounds(0, 0, 780, 600);
        this.infoPanel.setBounds(800, 0, 725, 600);

        this.add(this.boardPanel);
        this.add(this.infoPanel);

        this.setSize(1650, 1080);

        GameController.getInstance().startProgrammingTimer(this);
    }
}
