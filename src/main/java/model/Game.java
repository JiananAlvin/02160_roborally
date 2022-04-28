package model;

import content.OrientationEnum;
import content.RobotNameEnum;
import gui.game.GamePanel;
import lombok.Data;
import model.game.Room;
import model.game.board.map.GameMap;
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
    private int currentPlayerOrderedIndex;
    // TODO delete currentPlayer
    private Player currentPlayer;

    public Game() {
        this.participants = new ArrayList<>();
    }

    public static boolean validateMovement(Robot r, int row, int col) {
        boolean flag = false;
        if (r.getPosition().getRow() == 2) {
            flag = true;
        }
        if (!(row >= 0 && row < gameMap.getHeight() && col >= 0 && col < gameMap.getWidth())) {
            r.takeDamage(5);
            return false;
        }
        Tile tile = gameMap.getTileWithPosition(r.getPosition());
        if (tile instanceof Wall) { // current position is a wall
            if (flag) System.out.println("wall current position?????");
            return !((Wall) tile).getOrientation().equals(r.getOrientation());
        }
        tile = gameMap.getTileWithPosition(new Position(row, col));

        if (tile instanceof Wall) {  // next position is wall
            OrientationEnum tileOrientation = ((Wall) tile).getOrientation();
            if (flag) {
                System.out.println("wall next position?????");
                System.out.println("tileOrientation: " + tileOrientation);
                System.out.println("left orientation: " + r.getOrientation().getLeft());
                System.out.println("right orientation: " + r.getOrientation().getRight());
            }
            return (tileOrientation.equals(r.getOrientation().getLeft()) || tileOrientation.equals(r.getOrientation().getRight()))
                    || (tileOrientation.equals(r.getOrientation()));
        }
        return true;
    }


    public ArrayList<Player> getParticipants() {
        return participants;
    }


    public static GameMap getGameMap() {
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
        this.assignColorToParticipants();
    }

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
            Robot robot = new Robot(RobotNameEnum.valueOf((String) robotInfo.get(RobotController.RESPONSE_ROBOT_NAME)));
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

    public static Robot getRobotAtPosition(Position newPos) {
        for (Player player : participants) {
            if (player.getRobot().getPosition().equals(newPos)) {
                return player.getRobot();
            }
        }
        return null;
    }


    public void setParticipants(ArrayList<Player> orderOfPlayers) {
        participants = orderOfPlayers;
    }

    public void addParticipant(Player player) {
        participants.add(player);
    }

}
