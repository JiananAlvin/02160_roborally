package player.test;

import GlobalInformationManager.InfoManager;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Map;
import model.Robot;
import roborally.player.Player;
import roborally.player.Room;

import static org.junit.Assert.*;

public class StepsDefinition {
    Player player;
    Robot robot;
    Map map;

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
    	assertEquals(false, this.player.hasRobot());
    	
    }
    @When("choose a robot {string}")
    public void choose_a_robot(String string) {
    	this.robot = new Robot(string);
    	this.player.setRobot(this.robot);
    	this.player.setHasRobot(true);
    	
    	
    }
    @Then("{string} is assigned to this player")
    public void is_assigned_to_this_player(String string) {
    	assertEquals(true, this.player.getRobot().getName().equals(string));
    }
    
    @Given("having-a-robot status is true")
    public void having_a_robot_status_is_true() {
    	this.player.setHasRobot(true);
    	assertEquals(true, this.player.hasRobot());
    }
    @Given("robot-on-the-board status is false")
    public void robot_on_the_board_status_is_false() {
    	assertEquals(false,this.robot.onBoard());
    }
    @When("get initial position randomly")
    public void get_initial_position_randomly() {
        this.robot.setPosition(0,0);
        this.robot.setOnBoard(true);
    }
    @Then("Player is now at a position {string} and {string}")
    public void player_is_now_at_a_position_and(String string, String string2) {
    	assertEquals(true, this.robot.getCoordx() == Integer.parseInt(string)  && this.robot.getCoordy() == Integer.parseInt(string2));
    }

}
