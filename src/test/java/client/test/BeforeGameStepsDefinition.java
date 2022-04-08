package client.test;

import content.Application;
import content.RobotName;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import model.Game;
import model.Room;
import model.game.Player;
import model.game.board.map.element.Robot;

import static org.junit.Assert.*;


public class BeforeGameStepsDefinition {
    private Game game;
    private Player player;
    private Robot robot;
    private Room room;



    //----------------------------------------------------------------------------checked
    @Before
    public void init() {
        this.player = new Player();
        this.game = new Game();
    }

    @Given("a player opened the application")
    public void a_player_opened_the_application() {
        assertTrue(Application.getApplicationInstance().run());
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

//    @When("the player chooses a map {string}")
//    public void thePlayerChoosesAMap(String arg0) {
//        this.map = new GameMap(arg0);
//    }
//
//    @Then("this map {string} is displayed")
//    public void thisMapIsDisplayed(String arg0) {
//        assertEquals(arg0, this.map.getContent());
//    }

    //----------------------------------------------------------------------------checked
    @Given("having-a-robot status is false")
    public void having_a_robot_status_is_false() {
        assertFalse(this.player.hasRobot());
    }

    @When("choose a robot {string}")
    public void choose_a_robot(String string) {
        this.robot = new Robot(string);
        this.player.setRobot(this.robot);
    }

    @Then("{string} is assigned to this player")
    public void is_assigned_to_this_player(String string) {
        assertEquals(string, this.player.getRobot().getName());
    }



    @When("player enters a room with code number {int}")
    public void player_enters_a_room_with_code_number(Integer int1) {
        this.room = new Room(int1);
        this.game.addRoom(this.room);
    }

    @Then("player is in room {int}")
    public void player_is_in_room(Integer int1) {
        this.player.assignRoom(this.room);
    }

    @When("player creates a new room with code number {int}")
    public void player_creates_a_new_room_with_code_number(int int0) {
        this.room = new Room(int0);
        this.game.addRoom(this.room);
    }

    @Then("there is a new room with code {int} in the list of available rooms")
    public void there_is_a_new_room_with_code_in_the_list_of_available_rooms(Integer int1) {
        assertTrue(this.game.getRooms().contains(this.room));
    }
}










