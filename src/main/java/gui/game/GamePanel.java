package gui.game;

import content.App;
import content.MapNameEnum;
import content.RobotNameEnum;
import lombok.Data;
import lombok.SneakyThrows;
import model.Game;
import model.game.Room;
import model.game.Player;
import model.game.board.map.GameMap;
import model.game.board.map.element.Robot;
import model.game.board.mat.ProgrammingDeck;
import model.game.board.mat.RegisterArea;
import model.game.card.Card;
import model.game.proxy.PhaseManager;
import org.json.JSONArray;
import org.json.JSONObject;
import server.controller.ProgrammingRecordController;
import server.controller.RoomController;
import server.controller.UserController;

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

@Data
public class GamePanel extends JPanel {

    private BoardPanel boardPanel;
    private InfoPanel infoPanel;
    private MatPanel matPanel;
    public static final Color[] userColors = new Color[]{Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.MAGENTA};
    private Timer programmingTimer;
    private Timer activationPhaseTimer;
    public static final int MAX_PROGRAMMING_TIME = 30;
    public static final int ACTIVATION_PHASE_TIME = 2000;

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

        this.programmingTimer = this.invokeProgrammingTimer();
        this.programmingTimer.start();
    }

    private void reloadMatPanel() {
        if (this.matPanel != null)
            this.remove(matPanel);
        this.matPanel = new MatPanel();
        //this.matPanel.getLblRobot().setBackground(game.getUser().getPlayerColor());
        this.matPanel.setBounds(0, 600, 1650, 500);
        this.add(this.matPanel);
        this.revalidate();
        this.repaint();
    }

    public static void init(JFrame frame) {
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new GamePanel());
        //Display the window.
        frame.pack();
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1100, 800);
        frame.setVisible(true);
    }

    private Timer invokeProgrammingTimer() {

        // init the timer in ProgrammingPanel
        return new Timer(1000, new ActionListener() {
            int remainingTime = MAX_PROGRAMMING_TIME;

            public void actionPerformed(ActionEvent e) {
                if (remainingTime-- == MAX_PROGRAMMING_TIME) {
                    // the user draws cards
                    Game.getInstance().getUser().drawCards();
                    reloadMatPanel();
                }
                if (remainingTime >= 0)
                    matPanel.getLblTimer().setText("<html><br/>" + remainingTime + "&nbsp</html>");
                if (remainingTime < 0) {
                    Game game = Game.getInstance();
                    game.getUser().getRegisterArea().setRegisters(new ArrayList<>(game.getUser().getCardsInHand().subList(0, RegisterArea.REGISTER_QUEUE_SIZE)));
                    game.getUser().discard(new ArrayList<>(game.getUser().getCardsInHand().subList(0, ProgrammingDeck.NUMBER_OF_CARDS_DRAWN_IN_EACH_ROUND)));
                    game.getUser().getCardsInHand().removeAll(game.getUser().getCardsInHand());
                    new ProgrammingRecordController().createProgrammingRecord(
                            game.getUser().getName(),
                            game.getRoom().getRoomNumber(),
                            game.getCurrentRoundNum(),
                            getRegisterStr(game, 0),
                            getRegisterStr(game, 1),
                            getRegisterStr(game, 2),
                            getRegisterStr(game, 3),
                            getRegisterStr(game, 4)
                    );
                    programmingTimer.stop();
                    excuteProgRecordsWorker();
                    remainingTime = MAX_PROGRAMMING_TIME;
                    infoPanel.addLogToLogPanel("Programming phase done and inform worker to communicate with server", null);
                }
            }
        });
    }

    private void excuteProgRecordsWorker() {

        SwingWorker<JSONArray, Void> progRecordsWorker = new SwingWorker<>() {

            @Override
            protected JSONArray doInBackground() throws Exception {
                Game game = Game.getInstance();
                while (true) {
                    Thread.sleep(1000);
                    JSONObject temp = new ProgrammingRecordController().getProgrammingRecords(game.getRoom().getRoomNumber(), game.getCurrentRoundNum());
                    JSONArray programmingRecords = temp.getJSONArray(ProgrammingRecordController.RESPONSE_PROGRAMMING_RECORDS);
                    if (programmingRecords.length() == game.getParticipants().size()) {
                        return programmingRecords;
                    }
                }
            }

            @SneakyThrows
            @Override
            protected void done() {
                Game game = Game.getInstance();
                updateParticipantRegisters(get());
                infoPanel.addLogToLogPanel("ProgRecordsWorker done and start activation phase", null);
                activationPhaseTimer = invokeActivationPhaseTimer();
                activationPhaseTimer.start();
            }
        };
        // executes the swingworker on worker thread
        infoPanel.addLogToLogPanel("updateProgRecordsWorker starts", null);
        progRecordsWorker.execute();
    }

    private Timer invokeActivationPhaseTimer() {
        return new Timer(ACTIVATION_PHASE_TIME, new ActionListener() {
            @SneakyThrows
            @Override
            public void actionPerformed(ActionEvent e) {
                Game game = Game.getInstance();
                int round = game.getCurrentRoundNum();
                int registerIndex = game.getCurrentRegisterNum();
                int currenPlayerIndex = game.getCurrentPlayerIndex();
                if (registerIndex == 0 && currenPlayerIndex == 0) {
                    // the game starts
                    game.setParticipants(game.orderOfPlayers());
                }
                Player currentPlayer = game.getParticipants().get(currenPlayerIndex);

                Card currentRegisterCard = currentPlayer.getRegisterArea().getCard(registerIndex);
                boardPanel.getBoard()[currentPlayer.getRobot().getPosition().getRow()][currentPlayer.getRobot().getPosition().getCol()].unsetRobot();

                // performing the card and updating the robot Lives and checkpoint tokens of the user
                // If the Again card is put in the first register, perform nothing(the logic in CardAgain) on the robot.
                // Otherwise, perform the previous card on the robot.
                if ((currentRegisterCard.toString()).equals("CardAgain")) {
                    if (registerIndex != 0)
                        currentPlayer.getRegisterArea().getCard(registerIndex - 1).actsOn(currentPlayer.getRobot());
                } else {
                    currentRegisterCard.actsOn(currentPlayer.getRobot());
                }

                if ((currentPlayer.getName()).equals(game.getUser().getName())) {
                    infoPanel.addLogToLogPanel("Lives" + currentPlayer.getRobot().getLives(), null);
                    matPanel.getLblRobotLives().setText("Lives: " + currentPlayer.getRobot().getLives());
                    matPanel.getLblCheckpointToken().setText("<html><br/>" + currentPlayer.getObtainedCheckpointTokens().size() + "</html>");
                    matPanel.getLblDeckCards().setText("Programing Deck: " + game.getUser().getProgrammingDeck().getCards().size());
                    matPanel.getLblDiscardCards().setText("Discard Pile: " + game.getUser().getDiscardPile().getDiscards().size());
                }

                boardPanel.getBoard()[currentPlayer.getRobot().getPosition().getRow()][currentPlayer.getRobot().getPosition().getCol()].setRobot(currentPlayer.getRobot().getOrientation(), currentPlayer);
                boardPanel.repaint();

                infoPanel.addLogToLogPanel(currentPlayer.getRobot().getName() + ": " + currentPlayer.getRobot().getOrientation().toString(), currentPlayer);
                game.setCurrentPlayerIndex(++currenPlayerIndex);

                if (currenPlayerIndex == game.getParticipants().size()) {
                    // all players' one register finish
                    game.setCurrentRegisterNum(++registerIndex);

                    game.setCurrentPlayerIndex(0);

                    infoPanel.addLogToLogPanel("Robots start shooting", null);
                    PhaseManager.getInstance().executeRobotsShooting(game);

                }
                if (registerIndex == RegisterArea.REGISTER_QUEUE_SIZE) {
                    // one round finish
                    game.setCurrentRoundNum(++round);
                    // TODO debug
                    game.setCurrentRegisterNum(0);
                    activationPhaseTimer.stop();
                    infoPanel.addLogToLogPanel("activation phase done and start programming phase", null);
                    programmingTimer = invokeProgrammingTimer();
                    programmingTimer.start();
                }
            }
        });
    }

    private String getRegisterStr(Game game, int index) {
        return game.getUser().getRegisterArea().getRegisters().get(index).toString();
    }

    private void updateParticipantRegisters(JSONArray programmingRecords) {
        for (Object record : programmingRecords) {
            ArrayList<Card> cards = new ArrayList<>() {
                {
                    add(createCardInstance(((JSONObject) record).getString(ProgrammingRecordController.RESPONSE_REGISTER1)));
                    add(createCardInstance(((JSONObject) record).getString(ProgrammingRecordController.RESPONSE_REGISTER2)));
                    add(createCardInstance(((JSONObject) record).getString(ProgrammingRecordController.RESPONSE_REGISTER3)));
                    add(createCardInstance(((JSONObject) record).getString(ProgrammingRecordController.RESPONSE_REGISTER4)));
                    add(createCardInstance(((JSONObject) record).getString(ProgrammingRecordController.RESPONSE_REGISTER5)));
                }
            };
            for (Player player : Game.getInstance().getParticipants()) {
                if (player.getName().equals(((JSONObject) record).getString(ProgrammingRecordController.RESPONSE_USER))) {
                    player.getRegisterArea().setRegisters(cards);
                    break;
                }
            }
        }
    }

    @SneakyThrows
    private Card createCardInstance(String className) {
        final String PATH_TO_CARD_CLASS = "model.game.card.";
        Class<?> clz = Class.forName(PATH_TO_CARD_CLASS + className);
        return (Card) clz.getDeclaredConstructor().newInstance();
    }

//    @SneakyThrows
//    public static void main(String[] args) {
//        Player user = new Player("SpongeBob", new Robot(RobotNameEnum.SQUASH_BOT));
//        UserController userController = new UserController();
//        userController.deleteUser("SpongeBob");
//        userController.createUser("SpongeBob");
////        userController.createUser("PatrickStar");
//        //RobotController robotController = new RobotController();
//        userController.chooseRobot(user.getName(), user.getRobot().getName());
////        userController.chooseRobot("PatrickStar", "ZOOM_BOT");
//        RoomController roomController = new RoomController();
//        System.out.println(roomController.createRoom(user.getName(), "BEGINNER"));
//        int roomNumber = roomController.createRoom(user.getName(), "BEGINNER").getInt("room_number");
////        userController.joinRoom("PatrickStar", roomNumber);
//        GameMap gameMap = new GameMap(MapNameEnum.ADVANCED);
//        Room room = new Room(roomNumber);
//        Game game = new Game();
//        game.init(user, room, gameMap, roomController.roomInfo(roomNumber));
//
//        //Schedule a job for the event-dispatching thread:
//        //creating and showing this application's GUI.
//        javax.swing.SwingUtilities.invokeLater(new Runnable() {
//            public void run() {
//                JFrame frame = new JFrame(Application.APP_TITLE);
//                frame.setSize(1650, 1080);
//                frame.add(new GamePanel(game));
//                frame.setVisible(true);
////                roomController.deleteRoom(roomNumber);
//            }
//        });
//    }
}
