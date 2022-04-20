package model;

import lombok.Data;
import lombok.SneakyThrows;
import model.game.board.map.Collision;
import model.game.board.map.GameMap;
import model.game.board.map.Position;
import model.game.board.map.element.*;
import model.game.Player;
import org.json.JSONArray;
import org.json.JSONObject;
import server.controller.robot.RobotController;
import server.controller.room.RoomController;
import server.controller.user.UserController;

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
    private ArrayList<Player> participants;
    private Room room;
    private GameMap gameMap;
    private int currentRoundNum;
    private int currentRegisterNum;
    private Player currentPlayer;
    private Player winner;

    public Game() {
        this.participants = new ArrayList<>();
    }

    public void init(Room room, Player user, GameMap gameMap) {
        this.room = room;
        this.user = user;
        this.gameMap = gameMap;
        JSONObject roomInfoResponse = new RoomController().roomInfo(room.getRoomNumber());
        this.initParticipants(roomInfoResponse);
        this.generateRandomPositionsForAllParticipants();
    }


    /**
     * @param orderOfPlayers the arraylist of players sorted by their robots' distances to antenna
     * @param registerNum    it represents the register that is activated now (e.g. 1st register, 2nd register... not the general round)
     */
    @SneakyThrows
    public void executeRegisters(int registerNum, ArrayList<Player> orderOfPlayers) {
        for (Player player : orderOfPlayers) {
            this.currentPlayer = player;
            player.getRobot().applyCard(player.getRegisterArea().getCard(registerNum));
            if (this.gameMap.getTileWithPosition(player.getRobot().getPosition()).getClass().equals(CheckPoint.class))
                this.currentPlayer.takeToken((CheckPoint) this.gameMap.getTileWithPosition(player.getRobot().getPosition()));
        }
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
            Integer ycoord = p.getRobot().getPosition().getYcoord();
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
            this.participants.add(new Player(userName.toString(), new Robot((String) robotInfo.get(RobotController.RESPONSE_ROBOT_NAME))));
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
            player.getRobot().setPosition(assignedStartPoint.getPosition());
            new RobotController().updatePosition(player.getName(), player.getRobot().getPosition().getXcoord(), player.getRobot().getPosition().getYcoord());
        }
    }


    public void reboot(Robot r1) {
        r1.setLives(5);
        // the gameMap is null, so in order to test our functions is going to remain commented
        if (this.gameMap != null)
            r1.setPosition(this.gameMap.getARandomRebootPoint().getPosition());
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
           int collisionNumber = (collision.checkCollision(tile));
            switch (collisionNumber) {
                case 1: //laser, static gear
                    robotTakeDamage(r, ((Obstacle) tile).getDamage());
                    break;
                case 3:
                   // this.currentPlayer.resetCheckpoints();
                    this.reboot(r);
                    break;
            }
        }
    }

}
