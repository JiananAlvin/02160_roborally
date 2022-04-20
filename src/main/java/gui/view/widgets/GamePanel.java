package gui.view.widgets;

import content.MapName;
import content.RobotName;
import io.cucumber.java.sl.In;
import lombok.SneakyThrows;
import model.Game;
import model.game.Player;
import model.game.board.map.GameMap;
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
 */
public class GamePanel extends JPanel {
    private SimpleProgrammingMatPanel simpleProgrammingMatPanel;
    private InfoPanel infoPanel;
    private BoardPanel boardPanel;

    public GamePanel(Game game) {
        super(true);
        this.setLayout(null);
        this.simpleProgrammingMatPanel = new SimpleProgrammingMatPanel(game.getUser());
        this.infoPanel = new InfoPanel(game.getParticipants());
        this.boardPanel = new BoardPanel(game.getGameMap().getMapName());
        Dimension boardPanelDimension = this.boardPanel.getMinimumSize();
        this.boardPanel.setBounds(0, 0, boardPanelDimension.width, boardPanelDimension.height);
        this.add(boardPanel);
        this.infoPanel.setBounds(boardPanelDimension.width+10, 0, 400, 1000);
        this.add(infoPanel);
        this.simpleProgrammingMatPanel.setBounds(0, boardPanelDimension.height, boardPanelDimension.width, 50);
        this.add(simpleProgrammingMatPanel);
        this.setSize(boardPanelDimension.width + 400, Math.max(boardPanelDimension.width + 50, 1000));
    }


    public static void main(String[] args) {
        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            @SneakyThrows
            public void run() {
                JFrame frame = new JFrame("GridBagLayoutDemo");
                //Set up the content pane.
                Game game = new Game();
                game.setGameMap(new GameMap(MapName.STARTER));
                game.setUser(new Player("Test1", new Robot(RobotName.HAMMER_BOT)));
                game.addParticipant(new Player("Test1", new Robot(RobotName.HAMMER_BOT)));
                game.addParticipant(new Player("Test2", new Robot(RobotName.HULK_X90)));
                game.addParticipant(new Player("Test3", new Robot(RobotName.SPIN_BOT)));
                game.addParticipant(new Player("Test4", new Robot(RobotName.SQUASH_BOT)));
                game.addParticipant(new Player("Test5", new Robot(RobotName.TRUNDLE_BOT)));
                game.addParticipant(new Player("Test6", new Robot(RobotName.ZOOM_BOT)));
                frame.add(new GamePanel(game));
                //Display the window.
                frame.pack();
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.setSize(frame.getPreferredSize());
                frame.setVisible(true);
            }
        });
    }

}
