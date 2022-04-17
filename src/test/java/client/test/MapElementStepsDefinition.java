package client.test;

import content.MapName;
import content.RobotName;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Game;
import model.game.Player;
import model.game.board.map.GameMap;
import model.game.board.map.Orientation;
import model.game.board.map.Position;
import model.game.board.map.element.*;
import model.game.card.Card;
import model.game.card.programming.card.*;

import java.io.IOException;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class MapElementStepsDefinition {
    private Robot robot;
    private Game game;
    private Player p1;
    private Player p2;
    private Player p3;
    private Card card;
    private Tile tile;

    @Before
    public void initMapElement() {
        this.game = new Game();
    }

    //--------------------------------------------------------------------------------------------
    @Given("a player chose a robot {string}")
    public void aPlayerChoseARobot(String arg0) {
        this.robot = new Robot(arg0);
    }

    @When("the robot gets an initial position randomly")
    public void the_robot_gets_an_initial_position_randomly() {
        this.robot.setPosition(0, 0);
        // TODO:
        this.robot.setOnBoard(true);
    }

    @Then("robot is now at a position {string} and {string}")
    public void robotIsNowAtAPositionAnd(String arg0, String arg1) {
        assertEquals(new Position(Integer.parseInt(arg0), Integer.parseInt(arg1)), this.robot.getPosition());
    }


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


    //--------------------------------------------------------------------------------------------
    @Given("A robot {string} had {string} lives")
    public void aRobotHasLives(String arg0, String arg1) {
        this.robot = new Robot(arg0);
        this.robot.setLives(Integer.parseInt(arg1));
    }

    @When("The robot lives are reduced {string} points of damage by the game")
    public void theRobotLivesAreReducedPointsOfDamageByTheGame(String arg0) throws IOException {
        this.game = new Game();
        this.game.setGameMap(new GameMap(MapName.STARTER));
        this.game.robotTakeDamage(this.robot, Integer.parseInt(arg0));
    }

    @Then("The robot now has {string} lives")
    public void the_robot_has_lives(String lives) {
        assertEquals(Integer.parseInt(lives), this.robot.getLives());
    }


    @Given("A robot {string} has initial position {string} {string} with orientation {string}")
    public void aRobotHasInitialPositionWithOrientation(String robotName, String xPos, String yPos, String orientation) {
        this.robot = new Robot(robotName);
        this.robot.setPosition(Integer.parseInt(xPos), Integer.parseInt(yPos));
        Orientation o = Orientation.N;
        switch (orientation) {
            case "N":
                o = Orientation.N;
                break;
            case "S":
                o = Orientation.S;
                break;
            case "E":
                o = Orientation.E;
                break;
            case "W":
                o = Orientation.W;
                break;
        }
        this.robot.setOrientation(o);
    }

    @Given("A card with movement {string}")
    public void aCardWithMovement(String movement) {
        switch (movement) {
            case "1":
                card = new CardMove1();
                break;
            case "2":
                card = new CardMove2();
                break;
            case "3":
                card = new CardMove3();
                break;
            case "-1":
                card = new CardBackUp();
                break;
        }

    }

    @When("The card is played")
    public void theCardIsPlayed() {
        card.action(robot);
    }

    @Then("the robot position is {string} {string}")
    public void theRobotPositionIs(String expectedXPos, String expectedYPos) {
        assertEquals(Integer.parseInt(expectedXPos), robot.getPosition().getXcoord());
        assertEquals(Integer.parseInt(expectedYPos), robot.getPosition().getYcoord());
    }

    @Given("A robot {string} has position {string} {string}")
    public void aRobotHasPosition(String robotName, String xPos, String yPos) {
        this.robot = new Robot(robotName);
        this.robot.setPosition(Integer.parseInt(xPos), Integer.parseInt(yPos));
    }

    @Then("The expected output is {string} in a board that have a maximum size of {string} {string}")
    public void theExpectedOutputIsInABoardThatHaveAMaximumSizeOf(String output, String maxX, String maxY) {
        boolean aux = this.robot.imInsideBoard(Integer.parseInt(maxX), Integer.parseInt(maxY));
        assertEquals(Boolean.valueOf(output), aux);
    }


    //--------------------------------------------------------------------------------------------
    @Given("an antenna and three robots {string}, {string} and {string} chosen by {string}, {string} and {string} respectively")
    public void anAntennaAndThreeRobotsAndChosenByAndRespectively(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
        this.p1 = new Player(arg3, new Robot(arg0));
        this.p2 = new Player(arg4, new Robot(arg1));
        this.p3 = new Player(arg5, new Robot(arg2));
        this.game.addParticipant(p1);
        this.game.addParticipant(p2);
        this.game.addParticipant(p3);
    }

    @When("robotI, robotII and robotIII are placed in \\({string},{string}), \\({string},{string}),\\({string},{string}) respectively")
    public void robotiRobotIIAndRobotIIIArePlacedInRespectively(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
        this.p1.getRobot().setPosition(Integer.parseInt(arg0), Integer.parseInt(arg1));
        this.p2.getRobot().setPosition(Integer.parseInt(arg2), Integer.parseInt(arg3));
        this.p3.getRobot().setPosition(Integer.parseInt(arg4), Integer.parseInt(arg5));
    }

    @Then("the priority of these players is {string},{string},{string}")
    public void thePriorityOfThesePlayersIs(String arg0, String arg1, String arg2) {
        assertEquals(arg0, this.game.orderOfPlayers().get(0).getName());
        assertEquals(arg1, this.game.orderOfPlayers().get(1).getName());
        assertEquals(arg2, this.game.orderOfPlayers().get(2).getName());
    }


    //--------------------------------------------------------------------------------------------
    @Given("there is a game with map {string}")
    public void thereIsAGameWithMap(String arg0) throws IOException {
        this.game = new Game();
        this.game.setGameMap(new GameMap(arg0));
    }

    @And("there are players {string} and {string} in this game")
    public void thereArePlayersAndInThisGame(String arg0, String arg1) {
        Player player1 = new Player(arg0, new Robot("SQUASH BOT"));
        Player player2 = new Player(arg1, new Robot("ZOOM BOT"));
        this.game.addParticipant(player1);
        this.game.addParticipant(player2);
    }

    @And("this is {string} turn")
    public void thisIsTurn(String arg0) {
        for (Player player : this.game.getParticipants())
            if (player.getName().equals(arg0))
                this.game.setCurrentPlayer(player);
    }

    @And("this player's robot stops on the checkpoint {int}")
    public void thisPlayerSRobotStopsOnTheCheckPoint(int arg0) {
        CheckPoint checkPoint = this.game.getGameMap().getCheckPoints().get(arg0 - 1);
        this.game.getCurrentPlayer().getRobot().setPosition(checkPoint.getPosition());
    }

    @And("this player's robot taken checkpoint tokens from all previous checkpoints numerically and did not take a checkpoint token from this checkpoint {int}")
    public void thisPlayerSRobotTakenCheckpointTokensFromAllPreviousCheckpointsNumericallyAndDidNotTakeACheckpointTokenFromThisCheckpointPoint(int arg0) {
        int i = 1;
        for (CheckPoint checkPoint : this.game.getGameMap().getCheckPoints()) {
            if (i < arg0)
                this.game.getCurrentPlayer().getObtainedCheckpointTokens().add(checkPoint);
            i++;
        }
        assertEquals(arg0 - 1, this.game.getCurrentPlayer().getObtainedCheckpointTokens().size());
    }

    @When("this player's turn ends and the robot stops on this checkpoint {int}")
    public void thisPlayerSTurnEndsAndTheRobotStopsOnThisCheckpoint(int arg0) {
        assertTrue(this.game.getCurrentPlayer().takeToken(this.game.getGameMap().getCheckPoints().get(arg0 - 1)));
    }

    @Then("this player gets a checkpoint token from this checkpoint successfully and now has {int} checkpoint tokens")
    public void thisPlayerGetsACheckpointTokenFromThisCheckpointSuccessfullyAndNowHasCheckpointTokens(int arg0) {
        assertEquals(arg0, this.game.getCurrentPlayer().getObtainedCheckpointTokens().size());
    }


    //--------------------------------------------------------------------------------------------
    @And("The robot has initial position {string} {string} with orientation {string}")
    public void theRobotHasInitialPositionWithOrientation(String xPos, String yPos, String orientation) {
        this.robot.setPosition(Integer.parseInt(xPos), Integer.parseInt(yPos));
        Orientation o = Orientation.N;
        switch (orientation) {
            case "N":
                o = Orientation.N;
                break;
            case "S":
                o = Orientation.S;
                break;
            case "E":
                o = Orientation.E;
                break;
            case "W":
                o = Orientation.W;
                break;
        }
        this.robot.setOrientation(o);
    }

    @And("a position {string} {string} on the map indicating the obstacle of type {string}")
    public void aPositionOnTheMapIndicatingTheObstacle(String xPos, String yPos, String type) {
        switch (type) {
            case "wnl":
                this.tile = new WallNorthLaser(Integer.parseInt(xPos), Integer.parseInt(yPos));
                break;
            case "wsl":
                this.tile = new WallSouthLaser(Integer.parseInt(xPos), Integer.parseInt(yPos));
                break;
            case "wel":
                this.tile = new WallEastLaser(Integer.parseInt(xPos), Integer.parseInt(yPos));
                break;
            case "wwl":
                this.tile = new WallWestLaser(Integer.parseInt(xPos), Integer.parseInt(yPos));
                break;
            case "sg":
                this.tile = new StaticGear(Integer.parseInt(xPos), Integer.parseInt(yPos));
                break;
        }
    }

    @When("robot lands on an obstacle status is true")
    public void robotLandsOnAnObstacleStatusIsTrue() {
        this.game.checkCollisionTemporary(this.robot, this.tile);
    }
}
