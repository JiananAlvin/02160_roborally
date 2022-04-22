package gui.view.widgets.game;

import lombok.SneakyThrows;
import model.Game;
import model.game.Player;
import model.game.card.Card;
import org.json.JSONArray;
import org.json.JSONObject;
import server.controller.ProgrammingRecordController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;

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
 */
public class GamePanel extends JPanel {
    private SimpleProgrammingMatPanel simpleProgrammingMatPanel;
    private InfoPanel infoPanel;
    private BoardPanel boardPanel;
    private ArrayList<Icon> icons;
    public static final Color[] userColors = new Color[]{Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.MAGENTA};
    private Timer timeCalculator;
    private Timer timeActivationPhase;
    private SwingWorker<JSONArray, Void> updateProgRecordsWorker;
    public static final int MAX_WAITING_TIME = 10;

    public GamePanel(Game game) {
        super(true);
        this.setLayout(null);
        this.simpleProgrammingMatPanel = new SimpleProgrammingMatPanel(game);
        this.infoPanel = new InfoPanel(game.getParticipants(), game.getUser());
        this.boardPanel = new BoardPanel(game);
        Dimension boardPanelDimension = this.boardPanel.getMinimumSize();
        this.boardPanel.setBounds(0, 0, boardPanelDimension.width, boardPanelDimension.height);
        this.add(boardPanel);
        this.infoPanel.setBounds(boardPanelDimension.width + 10, 0, 400, 1000);
        this.add(infoPanel);
        this.simpleProgrammingMatPanel.setBounds(0, boardPanelDimension.height, boardPanelDimension.width, 50);
        this.add(simpleProgrammingMatPanel);
        this.setSize(boardPanelDimension.width + 400, Math.max(boardPanelDimension.width + 50, 1000));

        timeCalculator = this.createTimeCalculator(game);
        timeCalculator.start();


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

    private Timer createTimeCalculator(Game game) {
        // init the timer in ProgrammingPanel
        return new Timer(1000, new ActionListener() {
            int timeLeft = MAX_WAITING_TIME;

            public void actionPerformed(ActionEvent e) {
                timeLeft--;
                simpleProgrammingMatPanel.getLabelTimer().setText("LeftTime:" + timeLeft);
                if (timeLeft <= 0) {
                    JTextField[] registers = simpleProgrammingMatPanel.getRegisters();
                    new ProgrammingRecordController().createProgrammingRecord(game.getUser().getName(), game.getRoom().getRoomNumber(), game.getCurrentRoundNum(), registers[0].getText(), registers[1].getText(), registers[2].getText(), registers[3].getText(), registers[4].getText());
                    timeCalculator.stop();
                    startThread(game);
                    timeLeft = MAX_WAITING_TIME;
                    System.out.println("Start Timer createTimeCalculator");
                }
            }
        });
    }

    //TODO
    private Timer activationPhaseTimer(Game game) {
        return new Timer(1000, new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                int round = game.getCurrentRoundNum();
                int registerIndex = game.getCurrentRegisterNum();
                int currenPlayerIndex = game.getCurrentPlayerOrderedIndex();
                if (registerIndex == 0 && currenPlayerIndex == 0) {
                    game.setParticipants(game.orderOfPlayers());
                }
                Player currentPlayer = game.getParticipants().get(currenPlayerIndex);
                Card currentRegisterCard = currentPlayer.getRegisterArea().getCard(registerIndex);
                boardPanel.getBoard()[currentPlayer.getRobot().getPosition().getXcoord()][currentPlayer.getRobot().getPosition().getYcoord()].unsetRobot();
                currentRegisterCard.action(currentPlayer.getRobot());
                boardPanel.getBoard()[currentPlayer.getRobot().getPosition().getXcoord()][currentPlayer.getRobot().getPosition().getYcoord()].setRobot(currentPlayer.getRobot().getOrientation(),currentPlayer.getUserColor());

                boardPanel.repaint();
//                boardPanel.revalidate();
//                for (Player player : game.getParticipants()) {
//                    System.out.println(player.getName()+":"+player.getRobot().getPosition());
//                }
                // then update
                game.setCurrentPlayerOrderedIndex(++currenPlayerIndex);
                if (currenPlayerIndex == game.getParticipants().size()) {
                    game.setCurrentRegisterNum(++registerIndex);
                    game.setCurrentPlayerOrderedIndex(0);
                }
                if (registerIndex == 5) {
                    game.setCurrentRoundNum(++round);
                    game.setCurrentRegisterNum(0);
                    timeActivationPhase.stop();
                    timeCalculator.start();
                }


            }
        });
    }


    private void startThread(Game game) {

        updateProgRecordsWorker = new SwingWorker<JSONArray, Void>() {
            JSONArray result;

            @Override
            protected JSONArray doInBackground() throws Exception {
                while (true) {
                    Thread.sleep(1000);
                    JSONObject temp = new ProgrammingRecordController().getProgrammingRecords(game.getRoom().getRoomNumber(), game.getCurrentRoundNum());
                    JSONArray array = temp.getJSONArray(ProgrammingRecordController.RESPONSE);
                    if (array.length() == game.getParticipants().size()) {
                        this.result = array;
                        return array;
                    }
                }
            }


            @Override
            protected void done() {
                //TODO
                updateParticipantsRegister(result, game);
                System.out.println("updateProgRecordsWorker done and start time activation");
                timeActivationPhase = activationPhaseTimer(game);
                timeActivationPhase.start();
            }
        };
        // executes the swingworker on worker thread
        System.out.println("updateProgRecordsWorker starts");
        updateProgRecordsWorker.execute();
    }

    private void updateParticipantsRegister(JSONArray result, Game game) {
        for (Object record : result) {
            ArrayList<Card> cards = new ArrayList<>();
            cards.add(getCardObject(((JSONObject) record).getString(ProgrammingRecordController.RESPONSE_REGISTER1)));
            cards.add(getCardObject(((JSONObject) record).getString(ProgrammingRecordController.RESPONSE_REGISTER2)));
            cards.add(getCardObject(((JSONObject) record).getString(ProgrammingRecordController.RESPONSE_REGISTER3)));
            cards.add(getCardObject(((JSONObject) record).getString(ProgrammingRecordController.RESPONSE_REGISTER4)));
            cards.add(getCardObject(((JSONObject) record).getString(ProgrammingRecordController.RESPONSE_REGISTER5)));
            for (Player player : game.getParticipants()) {
                System.out.println(record);
                if (player.getName().equals(((JSONObject) record).getString(ProgrammingRecordController.RESPONSE_USER))) {
                    player.getRegisterArea().setRegisters(cards);
                    System.out.println(player.getName());
                    break;
                }
            }
        }
    }

    @SneakyThrows
    private Card getCardObject(String className) {
        final String PATH_TO_CARD_CLASS = "model.game.card.programming.Card";
        Class<?> clz = Class.forName(PATH_TO_CARD_CLASS + className);
        return (Card) clz.getDeclaredConstructor().newInstance();
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
