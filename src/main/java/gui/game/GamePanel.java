package gui.game;

import app.ClientRunner;
import controller.game.GameManager;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.SneakyThrows;
import model.Game;
import model.game.Player;
import model.game.board.mat.ProgrammingDeck;
import model.game.board.mat.RegisterArea;
import model.game.card.Card;
import org.json.JSONArray;
import org.json.JSONObject;
import controller.server.ProgrammingRecordController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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

        GameManager.getInstance().startProgrammingTimer(this);
    }
}
