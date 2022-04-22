package gui.view.widgets;

import gui.view.widgets.BoardPanel;
import gui.view.widgets.InfoPanel;
import gui.view.widgets.MatPanel;
import model.Game;
import model.Room;
import org.json.JSONArray;
import org.json.JSONObject;
import server.controller.ProgrammingRecordController;
import server.controller.RoomController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

/*
 * The whole GamePanel is like this
 * _____________________
 * |    board Panel |user1|
 * |                |_____|
 * |                |user2|
 * |                |-----|
 * |                |.log.|
 * |________________|_____|
 * |MatPanel              |
 * |________________ _____|
 */
public class GamePanel extends JPanel {
    private MatPanel matPanel;
    private InfoPanel infoPanel;
    private BoardPanel boardPanel;
    private ArrayList<Icon> icons;
    public static final Color[] userColors = new Color[]{Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.MAGENTA};
    private Timer timeCalculator;
    private Timer timeDisplayBoard;
    private SwingWorker<JSONObject, Void> updateProgRecordsWorker;
    public static final int MAX_WAITING_TIME = 10;

    public GamePanel(Game game) {
        super(true);
        this.setLayout(null);
        this.matPanel = new MatPanel(game);
        this.infoPanel = new InfoPanel(game.getParticipants(), game.getUser());
        //this.boardPanel = new BoardPanel(game);
        Dimension boardPanelDimension = this.boardPanel.getMinimumSize();
        this.boardPanel.setBounds(0, 0, boardPanelDimension.width, boardPanelDimension.height);
        this.add(boardPanel);
        this.infoPanel.setBounds(boardPanelDimension.width + 10, 0, 400, 1000);
        this.add(infoPanel);
        this.matPanel.setBounds(0, boardPanelDimension.height, boardPanelDimension.width, 50);
        this.add(matPanel);
        this.setSize(boardPanelDimension.width + 400, Math.max(boardPanelDimension.width + 50, 1000));

        timeCalculator = this.createTimeCalculator(game);
        timeCalculator.start();
        timeDisplayBoard = this.createDisplayTimer(game);
        timeDisplayBoard.start();
        startThread(game);
    }

    private Timer createTimeCalculator(Game game) {
        // init the timer in ProgrammingPanel
        return new Timer(1000, new ActionListener() {
            int timeLeft = MAX_WAITING_TIME;

            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                matPanel.getLblClock().setText("" + timeLeft);
                if (timeLeft <= 0) {
                    //new ProgrammingRecordController().createProgrammingRecord(game.getUser().getName(), game.getRoom().getRoomNumber(), game.getCurrentRoundNum(), registers[0].getText(), registers[1].getText(), registers[2].getText(), registers[3].getText(), registers[4].getText());
                    timeCalculator.stop();
                    startThread(game);
                }
            }
        });
    }

    //TODO
    private Timer createDisplayTimer(Game game) {
        return new Timer(1000, new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

            }
        });
    }

//    private Timer createSychronizeProgRecordsInfoTimer(Game game) {
//        return new Timer(500, new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//            }
//        });
//    }


    private void startThread(Game game) {

        updateProgRecordsWorker = new SwingWorker<JSONObject, Void>() {

            @Override
            protected JSONObject doInBackground() throws Exception {
                JSONObject result;
                int i = 0;
                do {
//                    Thread.sleep(4000);
                    result = new ProgrammingRecordController().getProgrammingRecords(game.getRoom().getRoomNumber(), game.getCurrentRoundNum());
//                    JSONArray users = result.getJSONArray(ProgrammingRecordController.);
                    System.out.println(result);

                }
                while (i++ < 20);
                return result;
            }


            @Override
            protected void done() {
                System.out.println("DONE!!!!!!!!!");
            }
        };

        // executes the swingworker on worker thread
        updateProgRecordsWorker.execute();
    }

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
