package client.test;

import content.Application;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import model.Game;
import model.Room;
import model.game.Player;
import model.game.board.map.element.Robot;
import org.json.JSONArray;
import org.json.JSONObject;
import server.controller.robot.RobotController;
import server.controller.room.RoomController;
import server.controller.user.UserController;

import java.util.ArrayList;

import static org.junit.Assert.*;


public class InitializationStepsDefinition {
    private Game game;
    private Player user;
    private Robot robot;
    private Room room;
    private JSONObject response;
    private Player roomOwner;

    @Before
    public void init() {
        this.user = new Player();
        this.game = new Game();
    }

    /**
     * @ removeRecordsInDataBase: Remove all the records in database generated in this test
     * TIP: the order of deleting operations is vital. Do not remove user firstly!!!
     */
    @After
    public void removeRecordsInDataBase() {
        try {
            new RobotController().deleteRobotInfo(this.user.getName());
        } catch (Exception e) {
//            System.out.println("There is no robot record to delete.");
        }
        try {
            new RoomController().deleteRoom(this.room.getRoomNumber());
        } catch (Exception e) {
//            System.out.println("There is no room record to delete.");
        }
        try {
            new UserController().deleteUser(this.user.getName());
        } catch (Exception e) {
//            System.out.println("There is no user record to delete.");
        }
        try {
            new UserController().deleteUser(this.roomOwner.getName());
        } catch (Exception e) {
//            System.out.println("There is no user record to delete.");
        }
    }

    //------------------------------------------------------------------------------------
    @Given("a player opened the application")
    public void a_player_opened_the_application() {
        assertTrue(Application.getApplicationInstance().run());
    }

    @When("the player inputs a name {string}")
    public void thePlayerInputsAName(String arg0) {
        this.user.setName(arg0);
        this.response = new UserController().createUser(arg0);
    }

    @Then("the player has the name {string}")
    public void thePlayerHasTheName(String arg0) {
        assertEquals(arg0, this.user.getName());
    }

    @Then("there is a new record in the collection user with username {string}")
    public void thereIsANewRecordInTheCollectionUserWithUsername(String arg0) {
        assertEquals(200, response.get("status"));
        UserController userController = new UserController();
        // try to insert again and find this user exist
        this.response = userController.createUser(arg0);
        assertEquals(400, response.get("status"));
    }


    //------------------------------------------------------------------------------------
    @Given("a player has a name {string}")
    public void aPlayerHasAName(String arg0) {
        this.user.setName(arg0);
        assertEquals(arg0, this.user.getName());
        this.response = new UserController().createUser(arg0);
        assertEquals(200, response.get("status"));
    }

    @When("the player chooses a robot {string}")
    public void thePlayerChoosesARobot(String string) {
        this.robot = new Robot(string);
        this.user.setRobot(this.robot);
        this.response = new UserController().chooseRobot(this.user.getName(), string);
    }

    @Then("{string} is assigned to this player")
    public void is_assigned_to_this_player(String string) {
        assertEquals(string, this.user.getRobot().getName());
        assertEquals(200, this.response.get("status"));
    }

    @And("there is a new record in the collection user with username {string} and robotname {string}")
    public void thereIsANewRecordInTheCollectionUserWithUsernameAndRobotname(String arg0, String arg1) {
        assertEquals(arg1, new RobotController().getRobotInfo(arg0).get("name"));
    }


    //------------------------------------------------------------------------------------
    // TODO: replace "code" with "room_number"
    @When("the player creates a new room and chooses a map {string}")
    public void thePlayerCreatesANewRoomAndChoosesAMap(String mapName) {
        this.room = new Room(mapName);
        this.game.setRoom(this.room);
        assertEquals(mapName, this.game.getRoom().getMapName());
        this.response = new RoomController().createRoom(this.user.getName(), mapName);
        this.game.getRoom().setRoomNumber((Integer) response.get("room_number"));
    }

    @Then("there is a new room record in the collection room")
    public void thereIsANewRoomRecordInTheCollectionRoom() {
        assertEquals(200, this.response.get("status"));
    }


    //----------------------------------------------------------------------------checked
    @Given("a room owner {string} creates a new room with map {string}")
    public void a_room_owner_creates_a_new_room_with_map(String string, String string2) {
        this.response = new UserController().createUser(string);
        assertEquals(200, this.response.get("status"));
        this.roomOwner = new Player();
        this.roomOwner.setName(string);
        this.response = new RoomController().createRoom(string, string2);
        assertEquals(200, this.response.get("status"));
        this.room = new Room(string2);
        this.room.setRoomNumber((Integer) this.response.get("room_number"));
    }

    @When("the player gets the room number from room owner and join this room")
    public void the_player_gets_the_room_number_from_room_owner_and_join_this_room() {
        new UserController().joinRoom(this.user.getName(), this.room.getRoomNumber());
    }

    @Then("the player is in this room")
    public void the_player_is_in_this_room() {
        this.response = new RoomController().roomInfo(this.room.getRoomNumber());
        JSONArray users = (JSONArray) this.response.get("users");
        assertEquals(this.user.getName(), users.getString(0));
        System.out.println(users.get(1).getClass());
    }
}










