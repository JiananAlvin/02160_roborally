package gui.view.widgets;

import content.MapName;
import content.RobotName;
import io.cucumber.java.sl.In;
import lombok.SneakyThrows;
import model.Game;
import model.game.Player;
import model.game.board.map.GameMap;
import model.game.board.map.element.Robot;
import server.controller.ProgrammingRecordController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
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
    private ArrayList<Icon> icons;

    public GamePanel(Game game) {
        super(true);
        this.setLayout(null);
        this.simpleProgrammingMatPanel = new SimpleProgrammingMatPanel(game);
        this.infoPanel = new InfoPanel(game.getParticipants(), game.getUser());
        this.boardPanel = new BoardPanel(game.getGameMap().getMapName());
        Dimension boardPanelDimension = this.boardPanel.getMinimumSize();
        this.boardPanel.setBounds(0, 0, boardPanelDimension.width, boardPanelDimension.height);
        this.add(boardPanel);
        this.infoPanel.setBounds(boardPanelDimension.width + 10, 0, 400, 1000);
        this.add(infoPanel);
        this.simpleProgrammingMatPanel.setBounds(0, boardPanelDimension.height, boardPanelDimension.width, 50);
        this.add(simpleProgrammingMatPanel);
        this.setSize(boardPanelDimension.width + 400, Math.max(boardPanelDimension.width + 50, 1000));

        simpleProgrammingMatPanel.setTimer(this.createTimeCalculator(game));
        simpleProgrammingMatPanel.getTimer().start();

    }

    private Timer createTimeCalculator(Game game) {
        // init the timer in ProgrammingPanel
        return new Timer(1000, new ActionListener() {
            int timeLeft = SimpleProgrammingMatPanel.MAX_WAITING_TIME;
            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                simpleProgrammingMatPanel.getLabelTimer().setText("LeftTime:" + timeLeft);
                if (timeLeft <= 0) {
                    JTextField[] registers = simpleProgrammingMatPanel.getRegisters();
                    new ProgrammingRecordController().createProgrammingRecord(game.getUser().getName(), game.getRoom().getRoomNumber(), game.getCurrentRoundNum(), registers[0].getText(), registers[1].getText(), registers[2].getText(), registers[3].getText(), registers[4].getText());
                    simpleProgrammingMatPanel.getTimer().stop();
                }
            }
        });
    }

//    private Timer createDisplayTimer(Game game){
//        return new Timer();
//    }

//    private Timer createSychronizeProgRecordsInfoTimer(){
//
//    }


    public static void init(JFrame frame, Game game) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new GamePanel(game));
        //Display the window.
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 800);
        frame.setVisible(true);
    }


//    public static void main(String[] args) {
//        //Schedule a job for the event-dispatching thread:
//        //creating and showing this application's GUI.
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            @SneakyThrows
//            public void run() {
//                JFrame frame = new JFrame("GridBagLayoutDemo");
//                //Set up the content pane.
//                Game game = new Game();
//                game.setGameMap(new GameMap(MapName.STARTER));
//                game.setUser(new Player("Test1", new Robot(RobotName.HAMMER_BOT)));
//                game.addParticipant(new Player("Test1", new Robot(RobotName.HAMMER_BOT)));
//                game.addParticipant(new Player("Test2", new Robot(RobotName.HULK_X90)));
//                game.addParticipant(new Player("Test3", new Robot(RobotName.SPIN_BOT)));
//                game.addParticipant(new Player("Test4", new Robot(RobotName.SQUASH_BOT)));
//                game.addParticipant(new Player("Test5", new Robot(RobotName.TRUNDLE_BOT)));
//                game.addParticipant(new Player("Test6", new Robot(RobotName.ZOOM_BOT)));
//                GamePanel.init(frame, game);
//            }
//        });
//    }

}
