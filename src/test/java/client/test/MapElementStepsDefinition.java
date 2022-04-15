package client.test;

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
    private Robot r1;
    private Robot r2;
    private Robot r3;
    private Card card;
    private Tile tile;

    @Before
    public void initMapElement() {
        this.game = new Game();
    }

    //==============ROBOT=======================================
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


    //----------------------------------------------------------------------------checked
    @Given("A robot {string} had {string} lives")
    public void aRobotHasLives(String arg0, String arg1) {
        this.robot = new Robot(arg0);
        this.robot.setLives(Integer.parseInt(arg1));
    }

    @When("The robot lives are reduced {string} points of damage by the game")
    public void theRobotLivesAreReducedPointsOfDamageByTheGame(String arg0) throws IOException {
        this.game = new Game();
        this.game.setGameMap(new GameMap("map1"));
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


    //==============ANTENA===============================================
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
    public void theRobotClosestToTheAntennaHasThePriorityToMove(String arg0, String arg1, String arg2) {
        assertEquals(arg0, this.game.orderOfRobots().get(0).getName());
        assertEquals(arg1, this.game.orderOfRobots().get(1).getName());
        assertEquals(arg2, this.game.orderOfRobots().get(2).getName());
    }

    //--------------------------------------------------------------------------------------------
    @Given("there is a game with map {string}")
    public void thereIsAGameWithMap(String arg0) throws IOException {
        this.game = new Game();
        game.setGameMap(new GameMap(arg0));
    }

    @And("there are players {string} and {string} in this game")
    public void thereArePlayersAndInThisGame(String arg0, String arg1) {
        Player player1 = new Player();
        Player player2 = new Player();
        player1.setName(arg0);
        player2.setName(arg1);
        player1.setRobot(new Robot("robot1"));
        player2.setRobot(new Robot("robot2"));
        game.addPlayer(player1);
        game.addPlayer(player2);
    }

    @And("this is {string} turn")
    public void thisIsTurn(String arg0) {
        int playerNum = this.game.findPlayerNum(arg0);
        if (playerNum == -1) return;
        this.game.setCurrentPlayerNum(playerNum);
    }

    @And("this player's robot has moved in this turn")
    public void thisPlayerSRobotHasMovedInThisTurn() {
        this.game.setHasInteractedWithCard(true);
    }

    @And("this player's robot is at the check point {int}")
    public void thisPlayerSRobotIsAtTheCheckPoint(int arg0) {
        CheckPoint checkPoint = this.game.getGameMap().getCheckPoints().get(arg0 - 1);
        this.game.findCurrentShownPlayer().getRobot().setPosition(checkPoint.getPosition());
    }

    @And("this player's robot has put marks on all numerically previous checkpoints and not put mark on this check point {int}")
    public void thisPlayerSRobotHasPutMarksOnAllNumericallyPreviousCheckpointsAndNotPutMarkOnThisCheckPoint(int arg0) {
        int i = 1;
        for (CheckPoint checkPoint : this.game.getGameMap().getCheckPoints()) {
            if (i < arg0)
                this.game.findCurrentShownPlayer().getAchievedCheckPoints().add(checkPoint);
            i++;
        }
        assertEquals(arg0 - 1, this.game.findCurrentShownPlayer().getAchievedCheckPoints().size());
    }

    @When("this player's turn ends and the robot stops at check point {int}")
    public void thisPlayerSTurnEndsAndTheRobotStopsAtCheckPoint(int arg0) {
        assertTrue(this.game.findCurrentShownPlayer().tryToAddMark(this.game.getGameMap().getCheckPoints().get(arg0 - 1)));
    }

    @Then("this player gets a new mark from this checkpoint successfully and now have {int} marks")
    public void thisPlayerGetsANewMarkFromThisCheckpointSuccessfully(int arg0) {
        assertEquals(arg0, this.game.findCurrentShownPlayer().getAchievedCheckPoints().size());
    }



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
        switch(type){
            case "wnl":
                tile = new WallNorthLaser(Integer.parseInt(xPos), Integer.parseInt(yPos));
                break;
            case "wsl":
                tile = new WallSouthLaser(Integer.parseInt(xPos), Integer.parseInt(yPos));
                break;
            case "wel":
                tile = new WallEastLaser(Integer.parseInt(xPos), Integer.parseInt(yPos));
                break;
            case "wwl":
                tile = new WallWestLaser(Integer.parseInt(xPos), Integer.parseInt(yPos));
                break;
            case "sg":
                tile = new StaticGear(Integer.parseInt(xPos), Integer.parseInt(yPos));
                break;
        }
    }

    @When("robot lands on an obstacle status is true")
    public void robotLandsOnAnObstacleStatusIsTrue() {
        initMapElement();

        game.checkCollisionTemporary(robot, tile );
    }

}
