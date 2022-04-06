package player.test;

import content.Application;
import content.RobotName;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import model.Game;
import model.Room;
import model.game.board.map.Map;
import model.game.Player;
import model.game.board.map.Orientation;
import model.game.board.map.element.Position;

//import model.game.board.map.element.ArrayBoard;
import model.game.board.map.element.Robot;
import model.game.card.Card;
import model.game.card.programming.card.CardTurnLeft;
import model.game.card.programming.card.CardTurnRight;
import model.game.card.programming.card.CardUTurn;

import static org.junit.Assert.*;

import java.util.ArrayList;

public class StepsDefinition {
    private Game game = new Game();
    private Player player;
    private Robot robot;
    private Map map;
    private ArrayList<Card> register;
    private Room room;
//    private ArrayBoard board;

    // ---------------------------------- new user stories immplemented
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


    // ------------------------------------------ end new stories

    //----------------------------------------------------------------------------checked
    @Before
    public void init() {
        this.player = new Player();
    }

    @Given("a player opened the application")
    public void a_player_opened_the_application() {
//        assertTrue(this.player.isPlaying());
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
//        this.player.setHasRobot(true);
    }

    @Then("{string} is assigned to this player")
    public void is_assigned_to_this_player(String string) {
        assertEquals(string, this.player.getRobot().getName());
    }

    //----------------------------------------------------------------------------checked
    @Given("having-a-robot status is true")
    public void having_a_robot_status_is_true() {
        assertTrue(this.player.hasRobot());
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

    //----------------------------------------------------------------------------checked
    //TODO:
    //  refactor this code later
//    @Given("prog_cards status is false")
//    public void prog_cards_status_is_false() {
//        assertFalse(this.player.progCardsStatus());
//    }

    @When("get programming cards")
    public void get_programming_cards() {
        this.register = this.player.getProgCards();
    }

    @Then("Player gets {int} cards")
    public void player_gets_cards(Integer int1) {
        assertEquals((int) int1, this.register.size());
    }

    //----------------------------------------------------------------------------checked
    private Robot r1;
    private Robot r2;
    private Robot r3;
    @Given("An antenna and three robots {string}, {string} and {string} in a game")
    public void anAntennaAndThreeRobotsAndInAGame(String arg0, String arg1, String arg2) {
        this.r1 = new Robot(arg0);
        this.r2 = new Robot(arg1);
        this.r3 = new Robot(arg2);
        this.game.addRobot(r1);
        this.game.addRobot(r2);
        this.game.addRobot(r3);

    }

    @When("RobotI, robotII and robotIII are placed in \\({string},{string}), \\({string},{string}),\\({string},{string}) respectively.")
    public void robotiRobotIIAndRobotIIIArePlacedInRespectively(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
        this.r1.setPosition(Integer.parseInt(arg0), Integer.parseInt(arg1));
        this.r2.setPosition(Integer.parseInt(arg2), Integer.parseInt(arg3));
        this.r3.setPosition(Integer.parseInt(arg4), Integer.parseInt(arg5));
    }

    @Then("The order of these robots is {string},{string},{string}.")
    public void theRobotClosestToTheAntennaHasThePriorityToMove (String arg0, String arg1, String arg2) {
        assertEquals(arg0, this.game.orderOfRobots().get(0).getName());
        assertEquals(arg1, this.game.orderOfRobots().get(1).getName());
        assertEquals(arg2, this.game.orderOfRobots().get(2).getName());
    }

    //----------------------------------------------------------------------------checked

    @Given("A robot was facing {string}")
    public void aRobotWasFacing(String arg0) {
        robot = new Robot(RobotName.HAMMER_BOT);
        robot.setOrientation(Orientation.valueOf(arg0));
    }

    @When("The robot changes its orientation by using the programming card {string}")
    public void theRobotChangesItsOrientationByUsingTheProgrammingCard(String arg0) {
        Card card;
        switch (arg0) {
            case "CardTurnLeft":
                card = new CardTurnLeft();
                break;
            case "CardTurnRight":
                card = new CardTurnRight();
                break;
            case "CardUTurn":
                card = new CardUTurn();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + arg0);
        }
        robot.applyCard(card);
    }

    @Then("The robot is now facing {string}")
    public void theRobotIsNowFacing(String arg0) {
        assertEquals(Orientation.valueOf(arg0), robot.getOrientation());
    }

    //----------------------------------------------------------------------------checked **
//    @Given("there exists a robot {string} on the board")
//    public void there_exists_a_robot_on_the_board(String robot) {
//        init();
//        this.robot = new Robot(robot);
//    }
//
//    @When("the board reads the card reavealed")
//    public void the_board_reads_the_card_reavealed() {
//        Card card = new CardMove1();
//        this.board = new ArrayBoard(robot);
//        board.ReadCard(card);
//    }
//
//    @Then("robot position is expected postition {string} {string}")
//    public void robot_position_is_expected_postition(String posx, String posy) {
//        assertEquals(robot.getCoordx(), Integer.parseInt(posx));
//        assertEquals(robot.getCoordy(), Integer.parseInt(posy));
//    }

    //----------------------------------------------------------------------------checked **
    // TODO:
    @Given("A robot {string} has {string} lives")
    public void aRobotHasLives(String arg0, String arg1) {
        robot = new Robot(arg0);
        robot.setLives(Integer.parseInt(arg1));
    }

    @When("The robot lives is reduced {string}")
    public void theRobotLivesIsReduced(String damage) {
        robot.takeDamage(Integer.parseInt(damage));
    }

    @Then("The robot is rebooted")
    public void the_robot_is_rebooted() {
// set the position of the robot to the coordinates of the reboot element
    }

    @Then("The robot has {string} lives")
    public void the_robot_has_lives(String lives) {
        robot.setLives(Integer.parseInt(lives));
    }
}
