package client.test;

import app.ClientRunner;
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
import controller.server.ServerConnection;
import controller.server.RobotController;
import controller.server.RoomController;
import controller.server.UserController;

import java.io.IOException;
import java.util.List;

import static org.junit.Assert.*;


public class InitializationStepsDefinition {
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
        // TODO
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
            new RoomController().deleteRoom(Game.getInstance().getRoom().getRoomNumber());
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
        Game.getInstance().removeData();
        GameMap.getInstance().removeData();
    }

    //1.------------------------------------------------------------------------------------
    @Given("a player opened the application")
    public void a_player_opened_the_application() {

    }

    @When("the player inputs a name {string}")
    public void thePlayerInputsAName(String arg0) throws InterruptedException {
        this.user.setName(arg0);
        Thread.sleep(200);
        this.response = new UserController().createUser(arg0);
    }

    @Then("the player has the name {string}")
    public void thePlayerHasTheName(String arg0) {
        assertEquals(arg0, this.user.getName());
    }

    @Then("there is a new record in the collection user with username {string}")
    public void thereIsANewRecordInTheCollectionUserWithUsername(String arg0) throws InterruptedException {
        assertEquals(200, response.get(ServerConnection.RESPONSE_STATUS));
    }


    //2.------------------------------------------------------------------------------------
    @When("the player creates a room with number {int}")
    public void thePlayerCreatesARoomWithNumber(int arg0) {
        this.room = new Room(arg0);
    }

    @Then("there is a new room with number {int}")
    public void thereIsANewRoomWithNumber(int arg0) {
        assertEquals(this.room.getRoomNumber(), arg0);
    }


    //3.------------------------------------------------------------------------------------
    @Given("a player has a name {string}")
    public void aPlayerHasAName(String arg0) throws InterruptedException {
        this.user.setName(arg0);
        assertEquals(arg0, this.user.getName());
        Thread.sleep(100);
        this.response = new UserController().createUser(arg0);
        assertEquals(200, response.get(ServerConnection.RESPONSE_STATUS));
    }

    @When("the player chooses a robot {string}")
    public void thePlayerChoosesARobot(String string) throws InterruptedException {
        this.robot = new Robot(RobotNameEnum.valueOf(string));
        this.user.setRobot(this.robot);
        Thread.sleep(100);
        this.response = new UserController().chooseRobot(this.user.getName(), string);
    }

    @Then("{string} is assigned to this player")
    public void is_assigned_to_this_player(String string) throws InterruptedException {
        assertEquals(string, this.user.getRobot().getName());
        Thread.sleep(100);
        assertEquals(200, this.response.get(ServerConnection.RESPONSE_STATUS));
    }

    @And("there is a new record in the collection user with username {string} and robotname {string}")
    public void thereIsANewRecordInTheCollectionUserWithUsernameAndRobotname(String arg0, String arg1) throws InterruptedException {
        Thread.sleep(100);
        assertEquals(arg1, new RobotController().getRobotInfo(arg0).get("name"));
    }


    //4.------------------------------------------------------------------------------------
    @When("the player creates a new room and chooses a map {string}")
    public void thePlayerCreatesANewRoomAndChoosesAMap(String mapName) throws IOException, InterruptedException {
        this.room = new Room(1023);
        Game.getInstance().setRoom(this.room);
        GameMap.getInstance().init(MapNameEnum.valueOf(mapName));
        assertEquals(mapName, GameMap.getInstance().getMapName());
        Thread.sleep(200);
        this.response = new RoomController().createRoom(this.user.getName(), mapName);
        Game.getInstance().getRoom().setRoomNumber((Integer) response.get(RoomController.RESPONSE_ROOM_NUMBER));
    }

    @Then("there is a new room record in the collection room")
    public void thereIsANewRoomRecordInTheCollectionRoom() {
        assertEquals(200, this.response.get(ServerConnection.RESPONSE_STATUS));
    }


    //5.------------------------------------------------------------------------------------
    @Given("a room owner {string} creates a new room with map {string}")
    public void a_room_owner_creates_a_new_room_with_map(String string, String string2) throws InterruptedException {
        Thread.sleep(100);
        this.response = new UserController().createUser(string);
        assertEquals(200, this.response.get(ServerConnection.RESPONSE_STATUS));
        this.roomOwner = new Player();
        this.roomOwner.setName(string);
        Thread.sleep(200);
        this.response = new RoomController().createRoom(string, string2);
        assertEquals(200, this.response.get(ServerConnection.RESPONSE_STATUS));
        this.room = new Room((Integer) this.response.get(RoomController.RESPONSE_ROOM_NUMBER));
    }

    @When("the player gets the room number from room owner and join this room")
    public void the_player_gets_the_room_number_from_room_owner_and_join_this_room() throws InterruptedException {
        Thread.sleep(100);
        new UserController().joinRoom(this.user.getName(), this.room.getRoomNumber());
    }

    @Then("the player is in this room")
    public void the_player_is_in_this_room() throws InterruptedException {
        Thread.sleep(100);
        this.response = new RoomController().roomInfo(this.room.getRoomNumber());
        JSONArray users = (JSONArray) this.response.get(RoomController.RESPONSE_USERS_IN_ROOM);
        assertEquals(this.user.getName(), users.getString(0));
    }


    //6.------------------------------------------------------------------------------------
    @SneakyThrows
    @Given("a room owner {string} created a new room with map {string} and chose robot {string}")
    public void aRoomOwnerCreatedANewRoomWithMapAndChoseRobot(String ownerName, String mapName, String robotName) {
        this.roomOwner = new Player(ownerName, new Robot(RobotNameEnum.valueOf(robotName)));
        Thread.sleep(100);
        assertEquals(200, new UserController().createUser(ownerName).get(ServerConnection.RESPONSE_STATUS));
        Thread.sleep(100);
        assertEquals(200, new UserController().chooseRobot(ownerName, robotName).get(ServerConnection.RESPONSE_STATUS));
        Thread.sleep(100);
        JSONObject createRoomResponse = new RoomController().createRoom(ownerName, mapName);
        assertEquals(200, createRoomResponse.get(ServerConnection.RESPONSE_STATUS));
        this.room = new Room(1023);
        GameMap.getInstance().init(MapNameEnum.valueOf(mapName));
        this.room.setRoomNumber((Integer) createRoomResponse.get(RoomController.RESPONSE_ROOM_NUMBER));
    }

    @And("player1 {string} player2 {string} and player3 {string} chose robot1 {string} robot2 {string} and robot3 {string} respectively")
    public void playerPlayerAndPlayerChoseRobotRobotAndRobotRespectively(String player1Name, String player2Name, String player3Name, String robot1Name, String robot2Name, String robot3Name) throws InterruptedException {
        this.player1 = new Player(player1Name, new Robot(RobotNameEnum.valueOf(robot1Name)));
        this.player2 = new Player(player2Name, new Robot(RobotNameEnum.valueOf(robot2Name)));
        this.player3 = new Player(player3Name, new Robot(RobotNameEnum.valueOf(robot3Name)));
        Thread.sleep(100);
        assertEquals(200, new UserController().createUser(this.player1.getName()).get(ServerConnection.RESPONSE_STATUS));
        Thread.sleep(100);
        assertEquals(200, new UserController().chooseRobot(this.player1.getName(), this.player1.getRobot().getName()).get(ServerConnection.RESPONSE_STATUS));
        Thread.sleep(100);
        assertEquals(200, new UserController().createUser(this.player2.getName()).get(ServerConnection.RESPONSE_STATUS));
        Thread.sleep(100);
        assertEquals(200, new UserController().chooseRobot(this.player2.getName(), this.player2.getRobot().getName()).get(ServerConnection.RESPONSE_STATUS));
        Thread.sleep(100);
        assertEquals(200, new UserController().createUser(this.player3.getName()).get(ServerConnection.RESPONSE_STATUS));
        Thread.sleep(100);
        assertEquals(200, new UserController().chooseRobot(this.player3.getName(), this.player3.getRobot().getName()).get(ServerConnection.RESPONSE_STATUS));
    }

    @And("player1 player2 and player3 joint this room")
    public void playerPlayerAndPlayerJointThisRoom() throws InterruptedException {
        Thread.sleep(100);
        assertEquals(200, new UserController().joinRoom(this.player1.getName(), this.room.getRoomNumber()).get(ServerConnection.RESPONSE_STATUS));
        Thread.sleep(100);
        assertEquals(200, new UserController().joinRoom(this.player2.getName(), this.room.getRoomNumber()).get(ServerConnection.RESPONSE_STATUS));
        Thread.sleep(100);
        assertEquals(200, new UserController().joinRoom(this.player3.getName(), this.room.getRoomNumber()).get(ServerConnection.RESPONSE_STATUS));
    }

    @When("the room owner starts the game and gets all information from server")
    public void theRoomOwnerStartsTheGameAndGetsAllInformationFromServer() throws InterruptedException {
        // the room owner click on the button 'start'
        Thread.sleep(100);
        JSONObject roomInfoResponse = new RoomController().roomInfo(this.room.getRoomNumber());
        JSONArray users = (JSONArray) roomInfoResponse.get(RoomController.RESPONSE_USERS_IN_ROOM);
        List<Object> userList = users.toList();
        assertEquals(4, userList.size());
        Game.getInstance().init(roomOwner.getName(), this.room, MapNameEnum.valueOf(GameMap.getInstance().getMapName()),roomInfoResponse);
    }

    @Then("the client of room owner generates all the initial positions and puts them to server")
    public void theClientOfRoomOwnerWillGenerateAllTheInitialPositionsAndPutsThemToServer() throws InterruptedException {
        for (Player player : Game.getInstance().getParticipants()) {
            // To check every player's robot gets initial position
            // If the server fails to assign a new position to this robot, the position of this robot is (0,0)
            Thread.sleep(100);
            JSONObject jsonObject = new RobotController().getRobotInfo(player.getName());
            assertFalse(0 == jsonObject.getInt(RobotController.RESPONSE_ROBOT_XCOORD) && 0 == jsonObject.getInt(RobotController.RESPONSE_ROBOT_YCOORD));
        }
    }

    @Then("player1 player2 and player3 successfully get their robot info from server")
    public void player1Player2AndPlayer3SuccessfullyGetTheirRobotInfoFromServer() throws InterruptedException {
        // Mock participants are trying to pull information from server
        Thread.sleep(100);
        JSONObject jsonObject1 = new RobotController().getRobotInfo(this.player1.getName());
        Thread.sleep(100);
        JSONObject jsonObject2 = new RobotController().getRobotInfo(this.player2.getName());
        Thread.sleep(100);
        JSONObject jsonObject3 = new RobotController().getRobotInfo(this.player3.getName());
        assertFalse(0 == jsonObject1.getInt(RobotController.RESPONSE_ROBOT_XCOORD) && 0 == jsonObject1.getInt(RobotController.RESPONSE_ROBOT_YCOORD));
        assertFalse(0 == jsonObject2.getInt(RobotController.RESPONSE_ROBOT_XCOORD) && 0 == jsonObject2.getInt(RobotController.RESPONSE_ROBOT_YCOORD));
        assertFalse(0 == jsonObject3.getInt(RobotController.RESPONSE_ROBOT_XCOORD) && 0 == jsonObject3.getInt(RobotController.RESPONSE_ROBOT_YCOORD));
    }

    //7.------------------------------------------------------------------------------------
    @And("player is in this room")
    public void playerIsInThisRoom() throws InterruptedException {
        Thread.sleep(100);
        assertEquals(200, new UserController().joinRoom(this.user.getName(), this.room.getRoomNumber()).get(ServerConnection.RESPONSE_STATUS));
    }

    @When("player want to exit this room")
    public void playerWantToExitThisRoom() throws InterruptedException {
        Thread.sleep(100);
        UserController userController = new UserController();
        assertEquals(200, userController.exitRoom(this.user.getName()).get(ServerConnection.RESPONSE_STATUS));
    }

    @Then("player is not in a room")
    public void playerIsNotInARoom() throws InterruptedException {
        Thread.sleep(100);
        JSONObject roomInfo = new RoomController().roomInfo(this.room.getRoomNumber());
        JSONArray users = (JSONArray) roomInfo.get(RoomController.RESPONSE_USERS_IN_ROOM);
        List<Object> userList = users.toList();
        for (Object user : userList) {
            assertNotEquals(this.user.getName(), user);
        }
    }


    //8.------------------------------------------------------------------------------------
    @Then("the status of the room is {string}")
    public void theStatusOfTheRoomIs(String arg0) throws InterruptedException {
        Thread.sleep(200);
        assertEquals(new RoomController().roomInfo(this.room.getRoomNumber()).get(RoomController.RESPONSE_ROOM_STATUS), arg0);
    }


    //9.------------------------------------------------------------------------------------
    @When("update the status of the room to {string}")
    public void updateTheStatusOfTheRoomTo(String arg0) throws InterruptedException {
        Thread.sleep(100);
        assertEquals(200, new RoomController().updateStatus(this.room.getRoomNumber(), arg0).get(ServerConnection.RESPONSE_STATUS));

    }
}










