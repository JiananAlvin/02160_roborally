package controller.game;

import app.ClientRunner;
import content.MapNameEnum;
import controller.server.ProgrammingRecordController;
import controller.server.RobotController;
import controller.server.RoomController;
import controller.server.UserController;
import gui.cover.CoverPanel;
import gui.game.GamePanel;
import gui.game.MatPanel;
import gui.login.LoginPanel;
import gui.room.RoomPanel;
import gui.waiting.WaitingPanel;
import lombok.SneakyThrows;
import model.Game;
import model.game.Player;
import model.game.Room;
import model.game.board.mat.ProgrammingDeck;
import model.game.board.mat.RegisterArea;
import model.game.card.Card;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Objects;

import static java.lang.Integer.parseInt;

public class GameManager {

    private static GameManager INSTANCE;
    SwingWorker<Void, Void> waitingPanelWorker;
    SwingWorker<JSONArray, Void> progRecordSyncWorker;
    private Timer programmingTimer;
    private Timer activationPhaseTimer;
    public static final int MAX_PROGRAMMING_TIME = 30;
    public static final int ACTIVATION_PHASE_TIME = 2000;

    private GameManager() {
    }

    public static GameManager getInstance() {
        if (GameManager.INSTANCE == null)
            GameManager.INSTANCE = new GameManager();
        return GameManager.INSTANCE;
    }


//    =====================================================LOGIN START==================================

    /**
     * When the programming run, create a LoginPanel and show it in frame.
     */
    public void showLoginPanel() {
        ClientRunner clientRunner = ClientRunner.getApplicationInstance();
        clientRunner.getFrame().setSize(880, 400);
        clientRunner.getFrame().setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        clientRunner.getFrame().setLocationRelativeTo(null); // this method display the JFrame to center position of a screen
        clientRunner.getFrame().getContentPane().add(new CoverPanel());
        clientRunner.getFrame().setVisible(true);
        Timer timer = new Timer(2000, e -> {
            clientRunner.getFrame().getContentPane().removeAll();
            clientRunner.getFrame().getContentPane().add(new LoginPanel());
            clientRunner.getFrame().setVisible(true);
        });
        timer.setRepeats(false);
        timer.start();
    }

    public void processLogin(String userName, String robotName) {
        /*
            fetching the username from the JTextField userName and the chosen robot when the "Login" button is pressed
            & inserting the username and the chosen robot's name into database through API
            & loading the RoomPanel
             */
        UserController userController = new UserController();
        userController.createUser(userName);
        if (userController.getResponse().get("status").equals(200)) {
            if ("".equals(robotName)) {
                JOptionPane.showMessageDialog(null, "Please choose a robot");
                userController.deleteUser(userName);
            } else {
                userController.chooseRobot(userName, robotName.replace(' ', '_'));
                JFrame frame = ClientRunner.getApplicationInstance().getFrame();
                frame.getContentPane().removeAll();
                frame.getContentPane().add(new RoomPanel(userName));
                frame.setVisible(true);
            }
        } else {
            JOptionPane.showMessageDialog(ClientRunner.getApplicationInstance().getFrame(), "Username already exists");
        }
    }
    //    =====================================================LOGIN END==================================

    //    =====================================================ROOM START==================================
    public void processCreateRoom(String userName, String mapName) {
        /*
             fetching the map name when the "Create room" button is pressed
             creating a room for the user through API
             */
        RoomController roomController = new RoomController();
        JSONObject response = roomController.createRoom(userName, mapName);
        String roomNumberStr = response.get("room_number").toString();

        JFrame frame = ClientRunner.getApplicationInstance().getFrame();
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new WaitingPanel(roomNumberStr, "owner", frame, userName));
        frame.setVisible(true);
    }

    public void processJoinRoom(String userName, String roomNumber) {
        /*
            fetching the room number when the "Join room" button is pressed
            inserting the qplayer info into the room through API
             */
        UserController userController = new UserController();
        userController.joinRoom(userName, Integer.parseInt(roomNumber));
        JFrame frame = ClientRunner.getApplicationInstance().getFrame();
        if (userController.getResponse().get("status").equals(200)) {
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new WaitingPanel(roomNumber, "participant", frame, userName));
            frame.setVisible(true);
        } else if (userController.getResponse().get("status").equals(400)) {
            JOptionPane.showMessageDialog(frame, "Room does not exist.", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void backToLoginPanle(String userName) {
        // deleting the user in database and returning to LoginPanel
        UserController userController = new UserController();
        userController.deleteUser(userName);
        JFrame frame = ClientRunner.getApplicationInstance().getFrame();
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new LoginPanel());
        frame.setVisible(true);
    }
    //    =====================================================ROOM END==================================

    //    =====================================================WAITING START==================================
    public void quitRoomForRoomOwner(String roomNumberStr) {
        // delete room and return to the RoomPanel
        RoomController roomController = new RoomController();
        roomController.deleteRoom(parseInt(roomNumberStr));
    }

    public void quitRoomForParticipants(String userName) {
        // exit room and return to the RoomPanel
        UserController userController = new UserController();
        userController.exitRoom(userName);
        JFrame frame = ClientRunner.getApplicationInstance().getFrame();
        frame.getContentPane().removeAll();
        frame.getContentPane().add(new RoomPanel(userName));
        frame.setVisible(true);
    }

    public void startGameForRoomOwner(String roomNumberStr, String userName, String signal) {
        int roomNumber = parseInt(roomNumberStr);
        JSONObject roomInfoResponse = new RoomController().roomInfo(roomNumber);
        try {
            startGamePanel(roomNumber, roomInfoResponse, userName, signal);
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }


    public void loadParticipantsTable(String userName, String roomNumberStr, String signal, WaitingPanel waitingPanel) {
        // initializing the table of participants
        // making a JTable non-editable
        //all cells false
        // updating the table of participants
        // update the stored timeStamp
        // centering all columns
        // putting the JTable "tabParticipants" inside a JScrollPane to show the TableHeader
        waitingPanelWorker = new SwingWorker<Void, Void>() {
            long timeStamp;

            @Override
            protected Void doInBackground() throws Exception {

                Thread.sleep(300);
                while (true) {
                    // initializing the table of participants
                    final String[][] data = {};
                    final String[] columnNames = {"Username", "Robot", "Identity"};
                    final DefaultTableModel tableModel = new DefaultTableModel(data, columnNames) {
                        // making a JTable non-editable
                        @Override
                        public boolean isCellEditable(int row, int column) {
                            //all cells false
                            return false;
                        }
                    };
                    // updating the table of participants
                    RoomController roomController = new RoomController();
                    RobotController robotController = new RobotController();
                    int roomNumber = parseInt(roomNumberStr);
                    JSONObject roomInfoResponse = roomController.roomInfo(roomNumber);

                    if (roomInfoResponse.get("status").equals(404)) {
                        SwingUtilities.invokeLater(() -> {
                            JFrame frame = ClientRunner.getApplicationInstance().getFrame();
                            frame.getContentPane().removeAll();
                            frame.getContentPane().add(new RoomPanel(userName));
                            frame.setVisible(true);
                        });
                        break;
                    }
                    if (roomInfoResponse.get(RoomController.RESPONSE_ROOM_STATUS).equals(RoomController.ROOM_STATUS_START)) {
                        if (!signal.equals("owner"))
                            try {
                                startGamePanel(roomNumber, roomInfoResponse, userName, signal);
                            } catch (Exception ignored) {
                            }
                        break;
                    }
                    long timeStampResponse = roomInfoResponse.getLong(RoomController.RESPONSE_REQUEST_TIME);
                    if (timeStampResponse > this.timeStamp) {
                        // update the stored timeStamp
                        this.timeStamp = timeStampResponse;
                        JSONArray users = (JSONArray) roomInfoResponse.get(RoomController.RESPONSE_USERS_IN_ROOM);
                        for (Object user : users) {
                            if (Objects.equals(user.toString(), roomController.roomInfo(roomNumber).getString(RoomController.RESPONSE_ROOM_OWNER))) {
                                tableModel.addRow(new Object[]{user.toString(), robotController.getRobotInfo(user.toString()).getString(RobotController.RESPONSE_ROBOT_NAME), "owner"});
                            } else {
                                tableModel.addRow(new Object[]{user.toString(), robotController.getRobotInfo(user.toString()).getString(RobotController.RESPONSE_ROBOT_NAME), "participant"});
                            }
                        }
                        SwingUtilities.invokeLater(() -> {
                            JTable tabParticipants = new JTable(tableModel);
                            // centering all columns
                            DefaultTableCellRenderer centerRenderer = new DefaultTableCellRenderer();
                            centerRenderer.setHorizontalAlignment(JLabel.CENTER);
                            tabParticipants.setDefaultRenderer(Object.class, centerRenderer);
                            // putting the JTable "tabParticipants" inside a JScrollPane to show the TableHeader
                            JScrollPane scrollPane = new JScrollPane(tabParticipants);
                            scrollPane.setBounds(100, 78, 680, 142);
                            waitingPanel.add(scrollPane);
                        });
                    }
                }
                return null;
            }
        };
        waitingPanelWorker.execute();
    }

    private void startGamePanel(int roomNumber, JSONObject roomInfoResponse, String userName, String signal) throws IOException {
        if (signal.equals("owner")) {
            new RoomController().updateStatus(roomNumber, "START");
        }
        String mapName = roomInfoResponse.getString(RoomController.RESPONSE_MAP_NAME);
        Game.getInstance().init(userName, new Room(roomNumber), MapNameEnum.valueOf(mapName), roomInfoResponse);
        SwingUtilities.invokeLater(() ->
        {
            JFrame frame = ClientRunner.getApplicationInstance().getFrame();
            frame.getContentPane().removeAll();
            frame.getContentPane().add(new GamePanel());
            //Display the window.
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            // set full screen.
//            ClientRunner.getDevice().setFullScreenWindow(frame);
            frame.setBounds(0, 0, 1920, 1080);
            frame.setVisible(true);
        });
    }

    //    =====================================================WAITING END==================================
    //    =====================================================GAME START==================================
    public void startProgrammingTimer(GamePanel gamePanel) {
        this.programmingTimer = this.invokeProgrammingTimer(gamePanel);
        this.programmingTimer.start();
    }

    private Timer invokeProgrammingTimer(GamePanel gamePanel) {

        // init the timer in ProgrammingPanel
        return new Timer(1000, new ActionListener() {
            int remainingTime = MAX_PROGRAMMING_TIME;

            public void actionPerformed(ActionEvent e) {
                if (remainingTime-- == MAX_PROGRAMMING_TIME) {
                    // the user draws cards
                    Game.getInstance().getUser().drawCards();
                    reloadMatPanel(gamePanel);
                }
                if (remainingTime >= 0)
                    gamePanel.getMatPanel().getLblTimer().setText("<html><br/>" + remainingTime + "&nbsp</html>");
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
                    excuteProgRecordsWorker(gamePanel);
                    remainingTime = MAX_PROGRAMMING_TIME;
                    gamePanel.getInfoPanel().addLogToLogPanel("Programming phase done and inform worker to communicate with server", null);
                }
            }
        });
    }

    private void reloadMatPanel(GamePanel gamePanel) {
        if (gamePanel.getMatPanel() != null)
            gamePanel.remove(gamePanel.getMatPanel());
        MatPanel matPanel = new MatPanel();
        //this.matPanel.getLblRobot().setBackground(game.getUser().getPlayerColor());
        matPanel.setBounds(0, 600, 1650, 500);
        gamePanel.setMatPanel(matPanel);
        gamePanel.add(matPanel);
        gamePanel.revalidate();
        gamePanel.repaint();
    }

    private void excuteProgRecordsWorker(GamePanel gamePanel) {

        progRecordSyncWorker = new SwingWorker<>() {
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
                gamePanel.getInfoPanel().addLogToLogPanel("ProgRecordsWorker done and start activation phase", null);
                activationPhaseTimer = invokeActivationPhaseTimer(gamePanel);
                activationPhaseTimer.start();
            }
        };
        // executes the swingworker on worker thread
        gamePanel.getInfoPanel().addLogToLogPanel("updateProgRecordsWorker starts", null);
        progRecordSyncWorker.execute();
    }

    private Timer invokeActivationPhaseTimer(GamePanel gamePanel) {
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
                gamePanel.getBoardPanel().getBoard()[currentPlayer.getRobot().getPosition().getRow()][currentPlayer.getRobot().getPosition().getCol()].unsetRobot();

                // performing the card and updating the robot Lives and checkpoint tokens of the user
                // If the Again card is put in the first register, perform nothing(the logic in CardAgain) on the robot.
                // Otherwise, perform the previous card on the robot.
                if ((currentRegisterCard.toString()).equals("CardAgain")) {
                    if (registerIndex != 0)
                        currentPlayer.getRegisterArea().getCard(registerIndex - 1).actsOn(currentPlayer.getRobot());
                } else {
                    currentRegisterCard.actsOn(currentPlayer.getRobot());
                }
                if (currentPlayer.getRobot().takeTokens())
                    if (currentPlayer.checkWin()) {
                        //game finish
                        int i = JOptionPane.showOptionDialog(ClientRunner.getApplicationInstance().getFrame(),
                                currentPlayer.getName() + " wins this game! The game will exist", "Game ends!",
                                JOptionPane.DEFAULT_OPTION, JOptionPane.INFORMATION_MESSAGE, null, null, null);
                        if (i == JOptionPane.OK_OPTION || i == JOptionPane.DEFAULT_OPTION || i == JOptionPane.NO_OPTION) {
                            ClientRunner.getApplicationInstance().getFrame().setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                            ClientRunner.getApplicationInstance().getFrame().dispose();
                        }
                    }


                if ((currentPlayer.getName()).equals(game.getUser().getName())) {
                    gamePanel.getInfoPanel().addLogToLogPanel("Lives" + currentPlayer.getRobot().getLives(), null);
                    gamePanel.getMatPanel().getLblRobotLives().setText("Lives: " + currentPlayer.getRobot().getLives());
                    gamePanel.getMatPanel().getLblCheckpointToken().setText("<html><br/>" + currentPlayer.getRobot().getCheckpoints().size() + "</html>");
                    gamePanel.getMatPanel().getLblDeckCards().setText("Programing Deck: " + game.getUser().getProgrammingDeck().getCards().size());
                    gamePanel.getMatPanel().getLblDiscardCards().setText("Discard Pile: " + game.getUser().getDiscardPile().getDiscards().size());
                }

                gamePanel.getBoardPanel().getBoard()[currentPlayer.getRobot().getPosition().getRow()][currentPlayer.getRobot().getPosition().getCol()].setRobot(currentPlayer.getRobot().getOrientation(), currentPlayer);
                gamePanel.getBoardPanel().repaint();

                gamePanel.getInfoPanel().addLogToLogPanel(currentPlayer.getRobot().getName() + ": " + currentPlayer.getRobot().getOrientation().toString(), currentPlayer);
                game.setCurrentPlayerIndex(++currenPlayerIndex);

                if (currenPlayerIndex == game.getParticipants().size()) {
                    // all players' one register finish
                    game.setCurrentRegisterNum(++registerIndex);

                    game.setCurrentPlayerIndex(0);

                    gamePanel.getInfoPanel().addLogToLogPanel("Robots start shooting", null);

                    for (Player player1 : game.getParticipants()) {
                        player1.getRobot().shoot(game);
                    }

                }
                if (registerIndex == RegisterArea.REGISTER_QUEUE_SIZE) {
                    // one round finish
                    game.setCurrentRoundNum(++round);
                    // TODO debug
                    game.setCurrentRegisterNum(0);
                    activationPhaseTimer.stop();
                    gamePanel.getInfoPanel().addLogToLogPanel("activation phase done and start programming phase", null);
                    programmingTimer = invokeProgrammingTimer(gamePanel);
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
    //    =====================================================GAME END==================================

    public void closeJFrame() {
        ClientRunner.getApplicationInstance().getFrame().dispose();
    }


}
