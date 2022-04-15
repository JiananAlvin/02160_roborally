package model;

import lombok.Data;
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
     * @ ArrayList<Robot> robotsInGame: the player of current turn
     * @ ArrayList<Player> players:  the players in this game
     * @ Room room : which room this game is based on
     * @ GameMap gameMap: which map this game is using
     * @ int currentTurnNum: the nth turn(outside turn)
     * @ int registerNum: the nth register runs currently(inner turn)
     * @ int currentPlayerNum: whose register is performing
     */
    private Player user;
    private ArrayList<Robot> robotsInGame;
    private ArrayList<Player> participants;
    private Room room;
    private GameMap gameMap;
    private int currentTurnNum;
    private int registerNum;
    private int currentPlayerNum;
    private boolean hasInteractedWithCard;


    public Game() {
        this.robotsInGame = new ArrayList<>();
        this.participants = new ArrayList<>();
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


    public void addRobot(Robot r1) {
        this.robotsInGame.add(r1);
    }

    public void reboot(Robot r1) {
        System.out.println("1");
        r1.setLives(5);
        System.out.println("2");
        // the gameMap is null, so in order to test our functions is going to remain commented
        //r1.setPosition(this.gameMap.getRebootPointRandomly().getPosition());
        System.out.println("3");

    }

    public void robotTakeDamage(Robot r, int damage) {
        if (!r.takeDamage(damage)) {
           this.reboot(r);
        }
    }

    public void addPlayer(Player p1) {
        this.participants.add(p1);
        this.robotsInGame.add(p1.getRobot());
    }

    public int findPlayerNum(String name){
        int i = 0;
        for (Player player : this.participants)
        {
            if(player.getName().equals(name)) return i;
            i++;
        }
        return -1;
    }

    public Player findCurrentShownPlayer(){
        return this.getParticipants().get(this.currentPlayerNum);
    }

    public boolean addMark(Player player, CheckPoint checkPoint) {
        if (participants.get(this.currentPlayerNum).getName().equals(player.getName()) && this.hasInteractedWithCard) {
            return player.tryToAddMark(checkPoint);
        }
        else return false;
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
    private void checkCollision(Player p1){
       Position pos =  p1.getRobot().getPosition();

       for(Tile[] tileArray : gameMap.getContent()){
           for(Tile tile : tileArray ){
             if(tile.getPosition().equals(pos))
             {
                //do some stuff
                Collision collision = new Collision();
                switch (collision.checkCollision(tile)){
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
    public void checkCollisionWithLaser(Robot r, Tile tile){
        Position pos =  r.getPosition();
        Collision collision = new Collision();
        if(tile.getPosition().equals(r.getPosition())) {
            if (collision.checkCollision(tile) == 1) { //laser/gear case
                // WallNorthLaser laser = (WallNorthLaser) tile ;
                robotTakeDamage(r, ((Obstacle)tile).getDamage());
            }
        }
    }




}
