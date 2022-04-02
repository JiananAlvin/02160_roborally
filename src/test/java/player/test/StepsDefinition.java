package player.test;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import model.Game;
import model.game.board.map.Map;
import model.game.Player;
import model.game.board.map.element.Position;
import model.game.board.map.element.Robot;
import model.game.card.Card;

import static org.junit.Assert.*;

import java.util.ArrayList;

public class StepsDefinition {
    private Player player;
    private Robot robot;
    private Map map;
    private ArrayList<Card> register;

    //----------------------------------------------------------------------------checked
    @Before
    public void init() {
        this.player = new Player();
    }

    @Given("a player started the game")
    public void aPlayerStartedTheGame() {
        assertTrue(this.player.isPlaying());
    }

    @When("player inputs a name {string}")
    public void playerInputsAName(String arg0) {
        this.player.setName(arg0);
    }

    @Then("this name is assigned to this player")
    public void thisNameIsAssignedToThisPlayer() {
        assertNotNull(this.player.getName());
    }

    //----------------------------------------------------------------------------checked **
    @Given("a player has a name {string}")
    public void aPlayerHasAName(String arg0) {
        this.player.setName(arg0);
        assertNotNull(this.player.getName());
    }

    @When("the player chooses a map {string}")
    public void thePlayerChoosesAMap(String arg0) {
        this.map = new Map(arg0);
    }

    @Then("this map {string} is displayed")
    public void thisMapIsDisplayed(String arg0) {
        assertEquals(arg0, this.map.getContent());
    }

    //----------------------------------------------------------------------------checked
    @Given("having-a-robot status is false")
    public void having_a_robot_status_is_false() {
        assertFalse(this.player.hasRobot());
    }

    @When("choose a robot {string}")
    public void choose_a_robot(String string) {
        this.robot = new Robot(string);
        this.player.setRobot(this.robot);
        this.player.setHasRobot(true);
    }

    @Then("{string} is assigned to this player")
    public void is_assigned_to_this_player(String string) {
        assertEquals(string, this.player.getRobot().getName());
    }

    //----------------------------------------------------------------------------checked
    @Given("having-a-robot status is true")
    public void having_a_robot_status_is_true() {
        this.player.setHasRobot(true);
    }

    @Given("robot-on-the-board status is false")
    public void robot_on_the_board_status_is_false() {
        assertFalse(this.robot.onBoard());
    }

    @When("get initial position randomly")
    public void get_initial_position_randomly() {
        this.robot.setPosition(0, 0);
        this.robot.setOnBoard(true);
    }

    @Then("Player is now at a position {string} and {string}")
    public void player_is_now_at_a_position_and(String string, String string2) {
        assertEquals(new Position(Integer.parseInt(string), Integer.parseInt(string2)), this.robot.getPosition());
    }

    //TODO:
//  refactor this code later
    @Given("prog_cards status is false")
    public void prog_cards_status_is_false() {
        assertFalse(this.player.progCardsStatus());
    }

    @When("get programming cards")
    public void get_programming_cards() {
        this.register = this.player.getProgCards();
    }

    @Then("Player gets {int} cards")
    public void player_gets_cards(Integer int1) {
        assertEquals((int) int1, this.register.size());
    }

    //----------------------------------------------------------------------------checked
    private Game game;
    private Robot r1;
    private Robot r2;
    private Robot r3;
    private ArrayList<Robot> robotsInGame;

    @Given("An antenna and two robots {string}, {string} and {string} in a game")
    public void anAntennaAndTwoRobotsAndInAGame(String arg0, String arg1, String arg2) {
        this.r1 = new Robot(arg0);
        this.r2 = new Robot(arg1);
        this.r3 = new Robot(arg2);
        this.robotsInGame = new ArrayList<Robot>() {
        };
        this.robotsInGame.add(r1);
        this.robotsInGame.add(r2);
        this.robotsInGame.add(r3);
        this.game = new Game(robotsInGame);
    }

    @When("RobotI, robotII and robotIII are placed in \\({string},{string}), \\({string},{string}),\\({string},{string}) respectively.")
    public void robotiRobotIIAndRobotIIIArePlacedInRespectively(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
        this.r1.setPosition(Integer.parseInt(arg0), Integer.parseInt(arg1));
        this.r2.setPosition(Integer.parseInt(arg2), Integer.parseInt(arg3));
        this.r3.setPosition(Integer.parseInt(arg4), Integer.parseInt(arg5));
    }

    @Then("The robot {string} closet to the antenna has the priority to move.")
    public void theRobotClosetToTheAntennaHasThePriorityToMove(String arg0) {
        assertEquals(arg0, this.game.turnOf(game.orderOfRobots()).getName());
    }
}
