package model;

import lombok.Data;
import model.game.board.map.Position;
import model.game.board.map.element.Robot;
import model.game.board.map.element.RebootPoint;
import model.game.Player;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

@Data
public class Game {

    private Player currentPlayer;
    private ArrayList<Room> rooms;
    private ArrayList<Robot> robotsInGame;
    private RebootPoint rebootPoint;
    private ArrayList<Player> players;

    public Game() {
        this.robotsInGame = new ArrayList<>();
        this.rebootPoint = new RebootPoint(new Position(2, 3));
        this.rooms = new ArrayList<>();
        this.players = new ArrayList<>();
    }

//    public Game(ArrayList<Robot> robotsInGame, RebootPoint rebootPoint) {
//        this.robotsInGame = robotsInGame;
//        this.rebootPoint = rebootPoint;
//        this.rooms = new ArrayList<>();
//        this.players = new ArrayList<>();
//    }

    public ArrayList<Robot> getRobotsInGame() {
        return this.robotsInGame;
    }

    /**
     * In case two robots have the same distance to the antenna. Imagine an invisible line
     * pointing straight out from the antenna's dish, once this line reaches the tied robots,
     * it moves clockwise, and the tied robots have priority according to the order in which
     * the line reaches them.
     */
    public ArrayList<Robot> orderOfRobots() {
        TreeMap<Integer, TreeMap<Integer, Robot>> robotDistanceTree = new TreeMap<>();
        for (Robot r : this.robotsInGame) {
            Integer dist = r.distanceToAntenna();
            Integer ycoord = r.getPosition().getYcoord();
            if (robotDistanceTree.containsKey(dist)) {
                robotDistanceTree.get(dist).put(ycoord, r);
            } else {
                // If two robots have the same distance to the antenna, the robot with larger ycoord has the priority.
                TreeMap<Integer, Robot> robotYcoordTree = new TreeMap<>(Comparator.reverseOrder());
                robotYcoordTree.put(ycoord, r);
                robotDistanceTree.put(dist, robotYcoordTree);
            }
        }
        ArrayList<Robot> order = new ArrayList<>();
        for (TreeMap<Integer, Robot> item : robotDistanceTree.values()) {
            order.addAll(item.values());
        }
        return order;
    }

    public Robot turnOf(ArrayList<Robot> order) {
        if (!order.isEmpty()) {
            return order.remove(0);
        } else {
            return turnOf(this.robotsInGame);
        }
    }

    public void addRoom(Room room) {
        this.rooms.add(room);
    }

    public ArrayList<Room> getRooms() {
        return this.rooms;
    }

    public void addRobot(Robot r1) {
        this.robotsInGame.add(r1);
    }

    public void reboot(Robot r1) {
        r1.setLives(5);
        r1.setPosition(this.rebootPoint.getPosition());
        //this.setPosition(); set position -> reboot position
    }

    public void robotTakeDamage(Robot r, int damage) {
        if (!r.takeDamage(damage)) {
            reboot(r);
        }
    }

    public void addPlayer(Player p1){
        this.players.add(p1);
        this.robotsInGame.add(p1.getRobot());
    }

//    public void playCards(Player p1){
//
//        ArrayList<Card> cardsSelected = p1.getProgCards();
//        for (Card card: cardsSelected)
//        {
//            card.action(p1.getRobot());
//        }
//
//    }

//    Prototype about how collisions will work
//    private void checkCollision(Player p1){
//       Position pos =  p1.getRobot().getPosition();
//
//       for(Tile tile : tilesInTheGame){
//           if(tile.getPosition().equals(pos)){
//
//           }
//       }
//    }

}
