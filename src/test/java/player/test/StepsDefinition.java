package player.test;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import model.game.element.board.Map;
import model.game.element.Player;
import model.game.element.board.map.element.Robot;
import model.game.element.card.Card;

import static org.junit.Assert.*;

import java.util.ArrayList;

public class StepsDefinition {
    private Player player;
    private Robot robot;
    private Map map;
    private ArrayList<Card> register;

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
        assertEquals(this.player.getRobot().getName(), string);
    }
    
    @Given("having-a-robot status is true")
    public void having_a_robot_status_is_true() {
    	this.player.setHasRobot(true);
        assertTrue(this.player.hasRobot());
    }
    @Given("robot-on-the-board status is false")
    public void robot_on_the_board_status_is_false() {
        assertFalse(this.robot.onBoard());
    }
    @When("get initial position randomly")
    public void get_initial_position_randomly() {
        this.robot.setPosition(0,0);
        this.robot.setOnBoard(true);
    }
    @Then("Player is now at a position {string} and {string}")
    public void player_is_now_at_a_position_and(String string, String string2) {
        assertTrue(this.robot.getCoordx() == Integer.parseInt(string) && this.robot.getCoordy() == Integer.parseInt(string2));
    }
    
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
    	assertTrue(this.register.size()==int1);

    }
  
}
