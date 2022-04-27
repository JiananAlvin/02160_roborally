package client.test;

import content.Application;
import content.MapNameEnum;
import content.RobotNameEnum;
import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

import lombok.SneakyThrows;
import model.Game;
import model.game.Room;
import model.game.Player;
import model.game.board.map.GameMap;
import model.game.board.map.element.Robot;
import org.json.JSONArray;
import org.json.JSONObject;
import server.controller.ServerConnection;
import server.controller.RobotController;
import server.controller.RoomController;
import server.controller.UserController;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;


public class InitializationStepsDefinition {
    private Game game;
    private Player user;
    private Robot robot;
    private Room room;
    private JSONObject response;
    private Player roomOwner;
    private Player player1;
    private Player player2;
    private Player player3;

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
        } catch (Exception ignored) {
        }
        try {
            new RoomController().deleteRoom(this.room.getRoomNumber());
        } catch (Exception ignored) {
        }
        try {
            new RoomController().deleteRoom(this.game.getRoom().getRoomNumber());
        } catch (Exception ignored) {
        }
        try {
            new UserController().deleteUser(this.user.getName());
        } catch (Exception ignored) {
        }
        try {
            new UserController().deleteUser(this.roomOwner.getName());
        } catch (Exception ignored) {
        }
        try {
            new UserController().deleteUser(this.player1.getName());
        } catch (Exception ignored) {
        }
        try {
            new UserController().deleteUser(this.player2.getName());
        } catch (Exception ignored) {
        }
        try {
            new UserController().deleteUser(this.player3.getName());
        } catch (Exception ignored) {
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
        assertEquals(200, response.get(ServerConnection.RESPONSE_STATUS));
        UserController userController = new UserController();
        // try to insert again and find this user exist
        this.response = userController.createUser(arg0);
        assertEquals(400, response.get(ServerConnection.RESPONSE_STATUS));
    }


    //------------------------------------------------------------------------------------
    @Given("a player has a name {string}")
    public void aPlayerHasAName(String arg0) {
        this.user.setName(arg0);
        assertEquals(arg0, this.user.getName());
        this.response = new UserController().createUser(arg0);
        assertEquals(200, response.get(ServerConnection.RESPONSE_STATUS));
    }

    @When("the player chooses a robot {string}")
    public void thePlayerChoosesARobot(String string) {
        this.robot = new Robot(RobotNameEnum.valueOf(string));
        this.user.setRobot(this.robot);
        this.response = new UserController().chooseRobot(this.user.getName(), string);
    }

    @Then("{string} is assigned to this player")
    public void is_assigned_to_this_player(String string) {
        assertEquals(string, this.user.getRobot().getName());
        assertEquals(200, this.response.get(ServerConnection.RESPONSE_STATUS));
    }

    @And("there is a new record in the collection user with username {string} and robotname {string}")
    public void thereIsANewRecordInTheCollectionUserWithUsernameAndRobotname(String arg0, String arg1) {
        assertEquals(arg1, new RobotController().getRobotInfo(arg0).get("name"));
    }


    //------------------------------------------------------------------------------------
    @SneakyThrows
    @When("the player creates a new room and chooses a map {string}")
    public void thePlayerCreatesANewRoomAndChoosesAMap(String mapName) {
        this.room = new Room();
        this.game.setRoom(this.room);
        this.game.setGameMap(new GameMap(MapNameEnum.valueOf(mapName)));
        assertEquals(mapName, this.game.getGameMap().getMapName());
        this.response = new RoomController().createRoom(this.user.getName(), mapName);
        this.game.getRoom().setRoomNumber((Integer) response.get(RoomController.RESPONSE_ROOM_NUMBER));
    }

    @Then("there is a new room record in the collection room")
    public void thereIsANewRoomRecordInTheCollectionRoom() {
        assertEquals(200, this.response.get(ServerConnection.RESPONSE_STATUS));
    }


    //----------------------------------------------------------------------------checked
    @Given("a room owner {string} creates a new room with map {string}")
    public void a_room_owner_creates_a_new_room_with_map(String string, String string2) {
        this.response = new UserController().createUser(string);
        assertEquals(200, this.response.get(ServerConnection.RESPONSE_STATUS));
        this.roomOwner = new Player();
        this.roomOwner.setName(string);
        this.response = new RoomController().createRoom(string, string2);
        assertEquals(200, this.response.get(ServerConnection.RESPONSE_STATUS));
        this.room = new Room();
        this.room.setRoomNumber((Integer) this.response.get(RoomController.RESPONSE_ROOM_NUMBER));
    }

    @When("the player gets the room number from room owner and join this room")
    public void the_player_gets_the_room_number_from_room_owner_and_join_this_room() {
        new UserController().joinRoom(this.user.getName(), this.room.getRoomNumber());
    }

    @Then("the player is in this room")
    public void the_player_is_in_this_room() {
        this.response = new RoomController().roomInfo(this.room.getRoomNumber());
        JSONArray users = (JSONArray) this.response.get(RoomController.RESPONSE_USERS_IN_ROOM);
        assertEquals(this.user.getName(), users.getString(0));
    }


    //------------------------------------------------------------------------------------
    @SneakyThrows
    @Given("a room owner {string} created a new room with map {string} and chose robot {string}")
    public void aRoomOwnerCreatedANewRoomWithMapAndChoseRobot(String ownerName, String mapName, String robotName) {
        this.roomOwner = new Player(ownerName, new Robot(RobotNameEnum.valueOf(robotName)));
        assertEquals(200, new UserController().createUser(ownerName).get(ServerConnection.RESPONSE_STATUS));
        assertEquals(200, new UserController().chooseRobot(ownerName, robotName).get(ServerConnection.RESPONSE_STATUS));
        JSONObject createRoomResponse = new RoomController().createRoom(ownerName, mapName);
        assertEquals(200, createRoomResponse.get(ServerConnection.RESPONSE_STATUS));
        this.room = new Room();
        this.game.setGameMap(new GameMap(MapNameEnum.valueOf(mapName)));
        this.room.setRoomNumber((Integer) createRoomResponse.get(RoomController.RESPONSE_ROOM_NUMBER));
    }

    @And("player1 {string} player2 {string} and player3 {string} chose robot1 {string} robot2 {string} and robot3 {string} respectively")
    public void playerPlayerAndPlayerChoseRobotRobotAndRobotRespectively(String player1Name, String player2Name, String player3Name, String robot1Name, String robot2Name, String robot3Name) {
        this.player1 = new Player(player1Name, new Robot(RobotNameEnum.valueOf(robot1Name)));
        this.player2 = new Player(player2Name, new Robot(RobotNameEnum.valueOf(robot2Name)));
        this.player3 = new Player(player3Name, new Robot(RobotNameEnum.valueOf(robot3Name)));
        assertEquals(200, new UserController().createUser(this.player1.getName()).get(ServerConnection.RESPONSE_STATUS));
        assertEquals(200, new UserController().chooseRobot(this.player1.getName(), this.player1.getRobot().getName()).get(ServerConnection.RESPONSE_STATUS));
        assertEquals(200, new UserController().createUser(this.player2.getName()).get(ServerConnection.RESPONSE_STATUS));
        assertEquals(200, new UserController().chooseRobot(this.player2.getName(), this.player2.getRobot().getName()).get(ServerConnection.RESPONSE_STATUS));
        assertEquals(200, new UserController().createUser(this.player3.getName()).get(ServerConnection.RESPONSE_STATUS));
        assertEquals(200, new UserController().chooseRobot(this.player3.getName(), this.player3.getRobot().getName()).get(ServerConnection.RESPONSE_STATUS));
    }

    @And("player1 player2 and player3 joint this room")
    public void playerPlayerAndPlayerJointThisRoom() {
        assertEquals(200, new UserController().joinRoom(this.player1.getName(), this.room.getRoomNumber()).get(ServerConnection.RESPONSE_STATUS));
        assertEquals(200, new UserController().joinRoom(this.player2.getName(), this.room.getRoomNumber()).get(ServerConnection.RESPONSE_STATUS));
        assertEquals(200, new UserController().joinRoom(this.player3.getName(), this.room.getRoomNumber()).get(ServerConnection.RESPONSE_STATUS));
    }

    @When("the room owner starts the game and gets all information from server")
    public void theRoomOwnerStartsTheGameAndGetsAllInformationFromServer() {
        // the room owner click on the button 'start'
        JSONObject roomInfoResponse = new RoomController().roomInfo(this.room.getRoomNumber());
        JSONArray users = (JSONArray) roomInfoResponse.get(RoomController.RESPONSE_USERS_IN_ROOM);
        List<Object> userList = users.toList();
        assertEquals(4, userList.size());
        this.game.initParticipants(roomInfoResponse);
        this.game.generateRandomPositionsForAllParticipants();
    }

    @Then("the client of room owner generates all the initial positions and puts them to server")
    public void theClientOfRoomOwnerWillGenerateAllTheInitialPositionsAndPutsThemToServer() {
        for (Player player : this.game.getParticipants()) {
            // To check every player's robot gets initial position
            // If the server fails to assign a new position to this robot, the position of this robot is (0,0)
            JSONObject jsonObject = new RobotController().getRobotInfo(player.getName());
            assertFalse(0 == jsonObject.getInt(RobotController.RESPONSE_ROBOT_XCOORD) && 0 == jsonObject.getInt(RobotController.RESPONSE_ROBOT_YCOORD));
        }
    }


    //------------------------------------------------------------------------------------
    @Then("player1 player2 and player3 successfully get their robot info from server")
    public void player1Player2AndPlayer3SuccessfullyGetTheirRobotInfoFromServer() {
        // Mock participants are trying to pull information from server
        JSONObject jsonObject1 = new RobotController().getRobotInfo(this.player1.getName());
        JSONObject jsonObject2 = new RobotController().getRobotInfo(this.player2.getName());
        JSONObject jsonObject3 = new RobotController().getRobotInfo(this.player3.getName());
        assertFalse(0 == jsonObject1.getInt(RobotController.RESPONSE_ROBOT_XCOORD) && 0 == jsonObject1.getInt(RobotController.RESPONSE_ROBOT_YCOORD));
        assertFalse(0 == jsonObject2.getInt(RobotController.RESPONSE_ROBOT_XCOORD) && 0 == jsonObject2.getInt(RobotController.RESPONSE_ROBOT_YCOORD));
        assertFalse(0 == jsonObject3.getInt(RobotController.RESPONSE_ROBOT_XCOORD) && 0 == jsonObject3.getInt(RobotController.RESPONSE_ROBOT_YCOORD));
    }

    @When("player want to exit this room")
    public void playerWantToExitThisRoom() {
        UserController userController = new UserController();
        assertEquals(200, userController.exitRoom(this.user.getName()).get(ServerConnection.RESPONSE_STATUS));
    }

    @And("player is in this room")
    public void playerIsInThisRoom() {
        assertEquals(200, new UserController().joinRoom(this.user.getName(), this.room.getRoomNumber()).get(ServerConnection.RESPONSE_STATUS));
    }

    @Then("player is not in a room")
    public void playerIsNotInARoom() {
        JSONObject roomInfo = new RoomController().roomInfo(this.room.getRoomNumber());
        JSONArray users = (JSONArray) roomInfo.get(RoomController.RESPONSE_USERS_IN_ROOM);
        List<Object> userList = users.toList();
        for(Object user : userList) {
            assertNotEquals(this.user.getName(), user);
        }
    }

    @Then("the status of the room is {string}")
    public void theStatusOfTheRoomIs(String arg0) {
        assertTrue(new RoomController().roomInfo(this.room.getRoomNumber()).get(RoomController.RESPONSE_ROOM_STATUS).equals(arg0));
    }

    @When("status of the room is updated to {string}")
    public void statusOfTheRoomIsUpdatedTo(String arg0) {
        assertTrue(new RoomController().updateStatus(this.room.getRoomNumber(), arg0).get(ServerConnection.RESPONSE_STATUS).equals(200));
    }
}










