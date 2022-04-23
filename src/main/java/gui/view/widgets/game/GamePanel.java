package gui.view.widgets.game;

import content.Application;
import lombok.SneakyThrows;
import model.Game;
import model.Room;
import model.game.Player;
import model.game.board.map.GameMap;
import model.game.board.map.element.Robot;
import model.game.card.Card;
import org.json.JSONArray;
import org.json.JSONObject;
import server.controller.ProgrammingRecordController;
import server.controller.RoomController;
import server.controller.UserController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
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

    private BoardPanel boardPanel;
    private InfoPanel infoPanel;
    private MatPanel matPanel;
    private ArrayList<Icon> icons;
    public static final Color[] userColors = new Color[]{Color.RED, Color.ORANGE, Color.YELLOW, Color.GREEN, Color.CYAN, Color.MAGENTA};
    private Timer timeCalculator;
    private Timer timeActivationPhase;
    private SwingWorker<JSONArray, Void> updateProgRecordsWorker;
    public static final int MAX_WAITING_TIME = 10;

    public GamePanel(Game game) {
        super(true);

        this.matPanel = new MatPanel(game);
        this.infoPanel = new InfoPanel(game.getParticipants(), game.getUser());
        this.boardPanel = new BoardPanel(game);

        this.setLayout(null);
        this.boardPanel.setBounds(0, 0, 780, 600);
        this.infoPanel.setBounds(800, 0, 725, 600);
        this.matPanel.setBounds(0, 600, 1650, 500);

        this.add(this.boardPanel);
        this.add(this.infoPanel);
        this.add(this.matPanel);
        this.setSize(1650, 1080);

        this.timeCalculator = this.createTimeCalculator(game);
        this.timeCalculator.start();
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
            int remainingTime = MAX_WAITING_TIME;

            public void actionPerformed(ActionEvent e) {
                remainingTime--;
                matPanel.getLblTimer().setText("<html><br/>" + remainingTime + "&nbsp</html>");
                if (remainingTime <= 0) {
                    new ProgrammingRecordController().createProgrammingRecord(game.getUser().getName(), game.getRoom().getRoomNumber(), game.getCurrentRoundNum(), "Move1", "Move1", "Move1", "Move1", "Move1");
                    timeCalculator.stop();
                    startThread(game);
                    remainingTime = MAX_WAITING_TIME;
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
                boardPanel.getBoard()[currentPlayer.getRobot().getPosition().getXcoord()][currentPlayer.getRobot().getPosition().getYcoord()].setRobot(currentPlayer.getRobot().getOrientation(), currentPlayer.getUserColor());

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

    @SneakyThrows
    public static void main(String[] args) {
        Player user = new Player("SpongeBob", new Robot("SQUASH_BOT"));
        UserController userController = new UserController();
        userController.deleteUser("SpongeBob");
        userController.createUser("SpongeBob");
        userController.createUser("PatrickStar");
        //RobotController robotController = new RobotController();
        userController.chooseRobot(user.getName(), user.getRobot().getName());
        userController.chooseRobot("PatrickStar", "ZOOM_BOT");
        RoomController roomController = new RoomController();
        System.out.println(roomController.createRoom(user.getName(), "STARTER"));
        int roomNumber = roomController.createRoom(user.getName(), "STARTER").getInt("room_number");
        userController.joinRoom("PatrickStar", roomNumber);
        GameMap gameMap = new GameMap("STARTER");
        Room room = new Room(roomNumber);
        Game game = new Game();
        game.init(user, room, gameMap, roomController.roomInfo(roomNumber));

        //Schedule a job for the event-dispatching thread:
        //creating and showing this application's GUI.
        javax.swing.SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                JFrame frame = new JFrame(Application.APP_TITLE);
                frame.setSize(1650, 1080);
                frame.add(new GamePanel(game));
                frame.setVisible(true);
                roomController.deleteRoom(roomNumber);
            }
        });

    }

}
