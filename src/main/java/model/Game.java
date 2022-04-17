package model;

import lombok.Data;
import lombok.SneakyThrows;
import model.game.board.map.Collision;
import model.game.board.map.GameMap;
import model.game.board.map.Position;
import model.game.board.map.element.*;
import model.game.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

@Data
public class Game {

    /**
     * @ Player user: the user of this application
     * @ ArrayList<Player> participants: the participants in this game
     * @ Room room: which room this game is based on
     * @ GameMap gameMap: which map this game is using
     * @ int currentRoundNum: the nth round
     * @ int currentRegisterNum: the nth register runs currently
     * @ int currentPlayer: whose turn of activation
     */
    private Player user;
    private ArrayList<Player> participants;
    private Room room;
    private GameMap gameMap;
    private int currentRoundNum;
    private int currentRegisterNum;
    private Player currentPlayer;

    public Game() {
        this.participants = new ArrayList<>();
    }

    /**
     * @param orderOfPlayers : the sorted arraylist of players according to their robots' distances to antenna
     * @param registerNum:   it represents the current turn of this activation phase(e.g. 1st register, 2nd register... not the general round)
     */
    @SneakyThrows
    public void executeRegisters(int registerNum, ArrayList<Player> orderOfPlayers) {
        for (Player player : orderOfPlayers) {
            this.currentPlayer = player;
            player.getRobot().applyCard(player.getRegisterArea().getCard(registerNum));
            if (this.gameMap.getTileWithPosition(player.getRobot().getPosition()).getClass().equals(CheckPoint.class))
                this.takeToken(player, (CheckPoint) this.gameMap.getTileWithPosition(player.getRobot().getPosition()));
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

    public void addParticipant(Player p1) {
        this.participants.add(p1);
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

    public int findPlayerNum(String name) {
        int i = 0;
        for (Player player : this.participants) {
            if (player.getName().equals(name)) return i;
            i++;
        }
        return -1;
    }


    public boolean takeToken(Player player, CheckPoint checkPoint) {
        if (this.currentPlayer.getName().equals(player.getName())) {
            return player.takeToken(checkPoint);
        } else return false;
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


}
