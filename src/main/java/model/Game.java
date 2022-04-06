package model;

import model.game.board.map.element.Position;
import model.game.board.map.element.Robot;
import model.game.board.map.element.RebootPoint;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.TreeMap;

/**
 * A class Game represents
 */

public class Game {

    private ArrayList<Robot> robotsInGame;
    private RebootPoint rebootPoint;

    public Game() {
        this.rebootPoint = new RebootPoint( new Position(2,3));
    }

    public Game(ArrayList<Robot> robotsInGame) {
        this.rebootPoint = new RebootPoint();
        this.robotsInGame = robotsInGame;

    }
    public Game(ArrayList<Robot> robotsInGame, RebootPoint rebootPoint) {
        this.robotsInGame = robotsInGame;
        this.rebootPoint = rebootPoint;
    }

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

    public void reboot(Robot r) {
        r.setLives(5);
        r.setPosition(this.rebootPoint.getPosition());
        //this.setPosition(); set position -> reboot position
    }

    public void robotTakeDamage(Robot r, int damage){
        if(!r.takeDamage(damage)){reboot(r);}
    }

}
