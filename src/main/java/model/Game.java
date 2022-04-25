package model;

import content.RobotName;
import gui.view.widgets.game.GamePanel;
import lombok.Data;
import model.game.board.map.Collision;
import model.game.board.map.GameMap;
import model.game.board.map.Orientation;
import model.game.board.map.Position;
import model.game.board.map.element.*;
import model.game.Player;
import org.json.JSONArray;
import org.json.JSONObject;
import server.controller.RobotController;
import server.controller.RoomController;

import java.util.*;

@Data

public class Game {
    /**
     * @ Player user: the user of this application
     * @ ArrayList<Player> participants: the participants in this game
     * @ Room room: the room for this game
     * @ GameMap gameMap: the map used in this game
     * @ int currentRoundNum: the nth round of programming
     * @ int currentRegisterNum: the nth register that is activated currently
     * @ Player currentPlayer: whose turn of activation
     */
    private Player user;
    private static ArrayList<Player> participants;
    private Room room;
    private static GameMap gameMap;
    private int currentRoundNum;
    private int currentRegisterNum;
    private boolean programmingPhaseOver;
    private Player winner;

    // TODO delete currentPlayer
    private Player currentPlayer;

    private int currentPlayerOrderedIndex;

    public Game() {
        this.participants = new ArrayList<>();
    }

    public static boolean validateMovement(Robot r, int row, int col) {

        if (!(row >= 0 && row < gameMap.getHeight() && col >= 0 && col < gameMap.getWidth())) {
            return false;
        }
        if (robotAt(row, col) != null) {
            Robot r1 = robotAt(row, col);
            r.setPosition(r1.getPosition());
            // temporary
            switch (r.getOrientation()) {
                case N:
                    r1.setPosition(new Position(r.getPosition().getRow() - 1, r.getPosition().getCol()));
                    break;
                case S:
                    r1.setPosition(new Position(r.getPosition().getRow() + 1, r.getPosition().getCol()));
                    break;
                case E:
                    r1.setPosition(new Position(r.getPosition().getRow(), r.getPosition().getCol() + 1));
                    break;
                case W:
                    r1.setPosition(new Position(r.getPosition().getRow(), r.getPosition().getCol() - 1));
                    break;

            }
        }
        Tile tile = gameMap.getTileWithPosition(r.getPosition());

        if (tile instanceof Wall) { // current position is a wall
            return !tile.getOrientation().equals(r.getOrientation());
        }
        tile = gameMap.getTileWithPosition(new Position(row, col));

        if(tile instanceof Wall) {  // next position is wall
            return !tile.getOrientation().getOpposite().equals(r.getOrientation());
        }
        return true;
    }

    private static Robot robotAt(int row, int col) {
        for (Player p : participants) {
            if (p.getRobot().getPosition().getRow() == row && p.getRobot().getPosition().getCol() == col) {
                return p.getRobot();
            }
        }
        return null;
    }

    public ArrayList<Player> getParticipants() {
        return participants;
    }


    public GameMap getGameMap() {
        return gameMap;
    }

    public void setGameMap(GameMap gameMap) {
        this.gameMap = gameMap;
    }

    /**
     * This method is used to init the whole game when the game starts.
     * For the room owner, the game starts only when room owner starts it.
     *
     * @param room
     * @param user
     * @param gameMap
     */
    public void init(Player user, Room room, GameMap gameMap, JSONObject roomInfoResponse) {
        this.room = room;
        this.user = user;
        this.gameMap = gameMap;
        this.currentRoundNum = 1;

        this.initParticipants(roomInfoResponse);
        // generate initial positions for all robots, only when the user is a room owner
        if (user.getName().equals(roomInfoResponse.getString(RoomController.RESPONSE_ROOM_OWNER)))
            this.generateRandomPositionsForAllParticipants();
//        give User different color
        this.assignColorToParticipants();
    }


//    /**
//     * @param orderOfPlayers the arraylist of players sorted by their robots' distances to antenna
//     * @param registerNum    it represents the register that is activated now (e.g. 1st register, 2nd register... not the general round)
//     */
//    @SneakyThrows
//    public void executeRegisters(int registerNum, ArrayList<Player> orderOfPlayers) {
//        for (Player player : orderOfPlayers) {
//            this.currentPlayer = player;
//            player.getRobot().applyCard(player.getRegisterArea().getCard(registerNum));
//            if (this.gameMap.getTileWithPosition(player.getRobot().getPosition()).getClass().equals(CheckPoint.class))
//                this.currentPlayer.takeToken((CheckPoint) this.gameMap.getTileWithPosition(player.getRobot().getPosition()));
//        }
//    }

    /**
     * In case two robots have the same distance to the antenna. Imagine an invisible line
     * pointing straight out from the antenna's dish, once this line reaches the tied robots,
     * it moves clockwise, and the tied robots have priority according to the order in which
     * the line reaches them.
     */
    public ArrayList<Player> orderOfPlayers() {
        TreeMap<Integer, TreeMap<Integer, Player>> robotDistanceTree = new TreeMap<>();
        for (Player p : this.participants) {
            Integer dist = p.getRobot().distanceToAntenna();
            Integer ycoord = p.getRobot().getPosition().getCol();
            if (robotDistanceTree.containsKey(dist)) {
                robotDistanceTree.get(dist).put(ycoord, p);
            } else {
                // If two robots have the same distance to the antenna, the robot with larger ycoord has the priority.
                TreeMap<Integer, Player> robotYcoordTree = new TreeMap<>(Comparator.reverseOrder());
                robotYcoordTree.put(ycoord, p);
                robotDistanceTree.put(dist, robotYcoordTree);
            }
        }
        ArrayList<Player> order = new ArrayList<>();
        for (TreeMap<Integer, Player> item : robotDistanceTree.values()) {
            order.addAll(item.values());
        }
        return order;
    }

    /**
     * This method is used to add a new player p1 to the participants in this game.
     * In the real game process, this method would not be used because there is another method called `initParticipants` in this class.
     *
     * @param p1 the player you want to add to participants.
     */
    public void addParticipant(Player p1) {
        this.participants.add(p1);
    }

    /**
     * This method creates new players according to the information in
     * JSONObject(Response of GET:/RoomInfo/[room_number]) and add them to participants
     *
     * @param roomInfoReponse the response from GET:/RoomInfo/[room_number].
     *                        It must be in JSON format data.
     *                        Its status should be 200. Otherwise there is not values for 'users'.
     */
    public void initParticipants(JSONObject roomInfoReponse) {
        JSONArray users = (JSONArray) roomInfoReponse.get(RoomController.RESPONSE_USERS_IN_ROOM);
        List<Object> userList = users.toList();
        for (Object userName : userList) {
            JSONObject robotInfo = new RobotController().getRobotInfo(userName.toString());
            Robot robot = new Robot(RobotName.valueOf((String) robotInfo.get(RobotController.RESPONSE_ROBOT_NAME)));
            try {
                // if JSONObject["x"] not found, it means there is no initial position
                int x = (int) robotInfo.get(RobotController.RESPONSE_ROBOT_XCOORD);
                int y = (int) robotInfo.get(RobotController.RESPONSE_ROBOT_YCOORD);
                robot.setInitialPosition(x, y);
            } catch (Exception e) {
                robot.setInitialPosition(0, 0);
            }
            this.participants.add(new Player(userName.toString(), robot));
        }
    }

    /**
     * This method is called only when the user is the room owner.
     * Only the room owner has the privilege to assign random positions for all robots.
     */
    public void generateRandomPositionsForAllParticipants() {
        ArrayList<StartPoint> startPoints = new ArrayList<>(this.gameMap.getStartPoints());
        for (Player player : this.participants) {
            StartPoint assignedStartPoint = startPoints.remove(new Random().nextInt(startPoints.size()));
            player.getRobot().setInitialPosition(assignedStartPoint.getPosition());
            new RobotController().updatePosition(player.getName(), player.getRobot().getPosition().getRow(), player.getRobot().getPosition().getCol());
        }
    }

    /**
     * Assign Colors to different participants according to their hash code.
     */
    public void assignColorToParticipants() {
        this.participants.sort(new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return o1.getName().hashCode() - o2.getName().hashCode();
            }
        });
        int i = 0;
        for (Player player : participants) {
            player.setUserColor(GamePanel.userColors[i++]);
        }
    }

    public void reboot(Robot r1) {
        r1.setLives(5);
        // the gameMap is null, so in order to test our functions is going to remain commented
        if (this.gameMap != null)
            r1.setInitialPosition(this.gameMap.getARandomRebootPoint().getPosition());
    }

    public void robotTakeDamage(Robot r, int damage) {
        if (!r.takeDamage(damage)) {
            this.reboot(r);
        }
    }

    // TODO:
    // Prototype about how collisions will work
    private void checkCollision(Player p1) {
        Position pos = p1.getRobot().getPosition();

        for (Tile[] tileArray : gameMap.getContent()) {
            for (Tile tile : tileArray) {
                if (tile.getPosition().equals(pos)) {
                    //do some stuff
                    Collision collision = new Collision();
                    switch (collision.checkCollision(tile)) {
                        case 1:
                            // WallNorthLaser laser = (WallNorthLaser) tile ;
                            robotTakeDamage(p1.getRobot(), 1);
                            break;
                    }
                }
            }
        }
    }

    // this function is a test from the one above
    public void checkCollisionTemporary(Robot r, Tile tile) {
        Position pos = r.getPosition();
        Collision collision = new Collision();
        if (tile.getPosition().equals(r.getPosition())) {
            if (collision.checkCollision(tile) == 1) { //laser/gear case
                // WallNorthLaser laser = (WallNorthLaser) tile ;
                robotTakeDamage(r, ((Obstacle) tile).getDamage());
            }
        }
    }

    public void endProgrammingPhase() {
        this.programmingPhaseOver = true;
    }

    public void setParticipants(ArrayList<Player> orderOfPlayers) {
        participants = orderOfPlayers;
    }


//    public boolean validMovement(Robot r, Position newPos) {
//        switch (gameMap.getContent()[r.getPosition().getRow()][r.getPosition().getCol()].getType()) {
//            case WallWest:
//                if (r.getOrientation()) {
//        }
//    }


}
