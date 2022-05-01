package model;

import content.MapNameEnum;
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

    private static Game INSTANCE;

    public static Game getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new Game();
        }
        return INSTANCE;
    }

    public void removeData() {
        INSTANCE = new Game();
    }


    private Game() {
    }

    /**
     * @ Player user: the user of this application
     * @ ArrayList<Player> participants: the participants in this game
     * @ Room room: the room for this game
     * @ GameMap gameMap: the map used in this game
     * @ int currentRoundNum: the nth round of programming
     * @ int currentRegisterNum: the nth register that is activated currently
     * @ Player currentPlayer: whose turn of activation
     */
    private String userName;
    private ArrayList<Player> participants;
    private Room room;
    private int currentRoundNum;
    private int currentRegisterNum;
    private Player winner;
    private int currentPlayerIndex;

    public Player getUser() {
        for (Player participant : this.participants) {
            if (this.userName.equals(participant.getName())) {
                return participant;
            }
        }
        return null;
    }

    /**
     * This method is used to init the whole game when the game starts.
     * For the room owner, the game starts only when room owner starts it.
     *
     * @param room
     * @param userName
     * @param mapName
     */
    public void init(String userName, Room room, MapNameEnum mapName, JSONObject roomInfoResponse) {
        System.out.println("Game init");
        System.out.println("mapName: " + mapName.getMapName());
        this.participants = new ArrayList<>();
        this.room = room;
        this.userName = userName;
        GameMap.getInstance().init(mapName);
        this.currentRoundNum = 1;
        this.initParticipants(roomInfoResponse);
        // generate initial positions for all robots, only when the user is a room owner
        if (userName.equals(roomInfoResponse.getString(RoomController.RESPONSE_ROOM_OWNER)))
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
    private void initParticipants(JSONObject roomInfoReponse) {
        JSONArray users = (JSONArray) roomInfoReponse.get(RoomController.RESPONSE_USERS_IN_ROOM);
        List<Object> userList = users.toList();
        for (Object userName : userList) {
            JSONObject robotInfo = new RobotController().getRobotInfo(userName.toString());
            Robot robot = new Robot(RobotNameEnum.valueOf((String) robotInfo.get(RobotController.RESPONSE_ROBOT_NAME)));
            try {
                // if JSONObject["x"] not found, it means there is no initial position
                int row = (int) robotInfo.get(RobotController.RESPONSE_ROBOT_XCOORD);
                int col = (int) robotInfo.get(RobotController.RESPONSE_ROBOT_YCOORD);
                robot.setPosition(row, col);
            } catch (Exception e) {
                robot.setPosition(0, 0);
            }
            this.participants.add(new Player(userName.toString(), robot));
        }
    }

    /**
     * This method is called only when the user is the room owner.
     * Only the room owner has the privilege to assign random positions for all robots.
     */
    private void generateRandomPositionsForAllParticipants() {
        ArrayList<StartPoint> startPoints = new ArrayList<>(GameMap.getInstance().getStartPoints());
        for (Player player : this.participants) {
            StartPoint assignedStartPoint = startPoints.remove(new Random().nextInt(startPoints.size()));
            player.getRobot().setPosition(assignedStartPoint.getPosition());
            new RobotController().updatePosition(player.getName(), player.getRobot().getPosition().getRow(), player.getRobot().getPosition().getCol());
        }
    }

    /**
     * Assign Colors to different participants according to their hash code.
     */
    private void assignColorToParticipants() {
        this.participants.sort(new Comparator<Player>() {
            @Override
            public int compare(Player o1, Player o2) {
                return o1.getName().hashCode() - o2.getName().hashCode();
            }
        });
        int i = 0;
        for (Player player : this.participants) {
            player.setPlayerColor(GamePanel.userColors[i++]);
        }
    }

    public Robot getRobotAtPosition(Position newPos) {
        for (Player player : this.participants) {
            if (player.getRobot().getPosition().equals(newPos)) {
                return player.getRobot();
            }
        }
        return null;
    }
}
