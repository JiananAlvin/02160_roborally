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
import model.game.card.programming.*;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MapElementStepsDefinition {
    private Robot robot;
    private Game game;
    private Player p1;
    private Player p2;
    private Player p3;
    private Card card;
    private Tile tile;
    private Position initialRobotPosition;

    @Before
    public void initMapElement() {
        this.game = new Game();
    }

    //--------------------------------------------------------------------------------------------
    @Given("a player chose a robot {string}")
    public void aPlayerChoseARobot(String arg0) {
        this.robot = new Robot(RobotName.valueOf(arg0));
    }
    @Given("there is a game with map {string}")
    public void thereIsAGameWithMap(String arg0) throws IOException {
        this.game = new Game();
        this.game.setGameMap(new GameMap(MapName.valueOf(arg0)));
    }
    @When("the robot gets an initial position randomly")
    public void the_robot_gets_an_initial_position_randomly() {
        this.robot.setInitialPosition(0, 0);
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
        this.robot = new Robot(RobotName.valueOf(arg0));
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


    @Given("a robot {string} had initial position {string} {string} with orientation {string}")
    public void aRobotHadInitialPositionWithOrientation(String robotName, String xPos, String yPos, String orientation) {
        this.robot = new Robot(RobotName.valueOf(robotName));
        this.robot.setInitialPosition(Integer.parseInt(xPos), Integer.parseInt(yPos));
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

    @Given("a card with movement {string}")
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

    @When("the card is played")
    public void theCardIsPlayed() {
        card.action(robot);
    }

    @Then("the robot position is {string} {string}")
    public void theRobotPositionIs(String expectedXPos, String expectedYPos) {
        assertEquals(Integer.parseInt(expectedXPos), robot.getPosition().getRow());
        assertEquals(Integer.parseInt(expectedYPos), robot.getPosition().getCol());
    }
    @Given("A robot {string} has position {string} {string}")
    public void aRobotHasPosition(String robotName, String xPos, String yPos) {
        this.robot = new Robot(RobotName.valueOf(robotName));
        this.robot.setInitialPosition(Integer.parseInt(xPos), Integer.parseInt(yPos));
        this.initialRobotPosition = new Position(Integer.parseInt(xPos), Integer.parseInt(yPos));
    }

    @Then("the expected output is {string} in a board that have a maximum size of {string} {string}")
    public void theExpectedOutputIsInABoardThatHaveAMaximumSizeOf(String output, String maxX, String maxY) {
        boolean aux = this.robot.imInsideBoard(Integer.parseInt(maxX), Integer.parseInt(maxY));
        assertEquals(Boolean.valueOf(output), aux);
    }


    //--------------------------------------------------------------------------------------------
    @Given("an antenna and three robots {string}, {string} and {string} chosen by {string}, {string} and {string} respectively")
    public void anAntennaAndThreeRobotsAndChosenByAndRespectively(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
        this.p1 = new Player(arg3, new Robot(RobotName.valueOf(arg0)));
        this.p2 = new Player(arg4, new Robot(RobotName.valueOf(arg1)));
        this.p3 = new Player(arg5, new Robot(RobotName.valueOf(arg2)));
        this.game.addParticipant(p1);
        this.game.addParticipant(p2);
        this.game.addParticipant(p3);
    }

    @When("robotI, robotII and robotIII are placed in \\({string},{string}), \\({string},{string}),\\({string},{string}) respectively")
    public void robotiRobotIIAndRobotIIIArePlacedInRespectively(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
        this.p1.getRobot().setInitialPosition(Integer.parseInt(arg0), Integer.parseInt(arg1));
        this.p2.getRobot().setInitialPosition(Integer.parseInt(arg2), Integer.parseInt(arg3));
        this.p3.getRobot().setInitialPosition(Integer.parseInt(arg4), Integer.parseInt(arg5));
    }

    @Then("the priority of these players is {string},{string},{string}")
    public void thePriorityOfThesePlayersIs(String arg0, String arg1, String arg2) {
        assertEquals(arg0, this.game.orderOfPlayers().get(0).getName());
        assertEquals(arg1, this.game.orderOfPlayers().get(1).getName());
        assertEquals(arg2, this.game.orderOfPlayers().get(2).getName());
    }


    //--------------------------------------------------------------------------------------------


    @And("there are players {string} and {string} in this game")
    public void thereArePlayersAndInThisGame(String arg0, String arg1) {
        Player player1 = new Player(arg0, new Robot(RobotName.SQUASH_BOT));
        Player player2 = new Player(arg1, new Robot(RobotName.ZOOM_BOT));
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

    @And("this player's robot has taken checkpoint tokens from all previous checkpoints numerically and did not take a checkpoint token from this checkpoint {int}")
    public void thisPlayerSRobotHasTakenCheckpointTokensFromAllPreviousCheckpointsNumericallyAndDidNotTakeACheckpointTokenFromThisCheckpointPoint(int arg0) {
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
    // Check this game has finished
    @Then("this game checks game status and now the game status is {string}")
    public void thisGameChecksGameStatusAndNowTheGameStatusIs(String arg0) {
        if (this.game.getCurrentPlayer().getObtainedCheckpointTokens().size() == this.game.getGameMap().getCheckPoints().size())
            this.game.setWinner(this.game.getCurrentPlayer());
        if (arg0.equals("finished"))
            assertEquals(this.game.getCurrentPlayer(), this.game.getWinner());
        else assertNull(this.game.getWinner());
    }


    //--------------------------------------------------------------------------------------------
    @And("The robot has initial position {string} {string} with orientation {string}")
    public void theRobotHasInitialPositionWithOrientation(String xPos, String yPos, String orientation) {
        this.robot.setInitialPosition(Integer.parseInt(xPos), Integer.parseInt(yPos));
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
                this.tile = new Laser(Integer.parseInt(xPos), Integer.parseInt(yPos), false);
                break;
            case "wsl":
                this.tile = new Laser(Integer.parseInt(xPos), Integer.parseInt(yPos), false);
                break;
            case "wel":
                this.tile = new Laser(Integer.parseInt(xPos), Integer.parseInt(yPos), true);
                break;
            case "wwl":
                this.tile = new Laser(Integer.parseInt(xPos), Integer.parseInt(yPos), true);
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


    @And("there is a wall {string} at the same position as the robot")
    public void thereIsAWallAtTheSamePositionAsTheRobot(String arg0) {
        switch (arg0) {
            case "ww":
                this.tile = new WallWest(this.robot.getPosition().getCol(), this.robot.getPosition().getRow());
                break;
            case "we":
                this.tile = new WallEast(this.robot.getPosition().getCol(), this.robot.getPosition().getRow());
                break;
            case "ws":
                this.tile = new WallSouth(this.robot.getPosition().getCol(), this.robot.getPosition().getRow());
                break;
            case "wn":
                this.tile = new WallNorth(this.robot.getPosition().getCol(), this.robot.getPosition().getRow());
                break;
            case "wsl":
                this.tile = new WallSouthLaser(this.robot.getPosition().getCol(), this.robot.getPosition().getRow());
                break;
            case "wnl":
                this.tile = new WallNorthLaser(this.robot.getPosition().getCol(), this.robot.getPosition().getRow());
                break;
            case "wel":
                this.tile = new WallEastLaser(this.robot.getPosition().getCol(), this.robot.getPosition().getRow());
                break;
            case "wwl":
                this.tile = new WallWestLaser(this.robot.getPosition().getCol(), this.robot.getPosition().getRow());
                break;

        }
    }


    @And("the robot faces the wall")
    public void theRobotFacesTheWall() {
        switch (this.tile.getClass().getSimpleName()) {
            case "WallWest":
            case "WallWestLaser":
                this.robot.setOrientation(Orientation.W);
                break;
            case "WallEast":
            case "WallEastLaser":
                this.robot.setOrientation(Orientation.E);
                break;
            case "WallNorth":
            case "WallNorthLaser":
                this.robot.setOrientation(Orientation.N);
                break;
            case "WallSouth":
            case "WallSouthLaser":
                this.robot.setOrientation(Orientation.S);
                break;

        }

    }

    @When("robot tries to move forward")
    public void robotTriesToMoveForward() {
        switch (this.robot.getOrientation()) {
            case N:
                this.robot.setPosition(this.robot.getPosition().getCol(), this.robot.getPosition().getRow() - 1);
                break;
            case S:
                this.robot.setPosition(this.robot.getPosition().getCol(), this.robot.getPosition().getRow() + 1);
                break;
            case E:
                this.robot.setPosition(this.robot.getPosition().getCol() + 1, this.robot.getPosition().getRow());
                break;
            case W:
                this.robot.setPosition(this.robot.getPosition().getCol() - 1, this.robot.getPosition().getRow());
                break;
        }
    }

    @Then("robot does not move forward")
    public void robotDoesNotMoveForward() {
        assertTrue(this.initialRobotPosition.equals(this.robot.getPosition()));
    }

    private Robot robot2;
    @And("robot faces another robot {string} with position {string} {string}")
    public void robotFacesAnotherRobotWithPosition(String arg0, String arg1, String arg2) {
        this.robot2 = new Robot(arg0, Integer.parseInt(arg1), Integer.parseInt(arg2));
    }

    @When("programming phase is over")
    public void programmingPhaseIsOver() {
        this.game.endProgrammingPhase();
    }

    @Then("robot{int} shoots robot{int}")
    public void robotShootsRobot(int arg0, int arg1) {
        int lives = this.robot2.getLives();
        this.robot.shoot(this.robot2);
        assertTrue(this.robot2.getLives()==lives-1);
    }



    @When("robot tries to move past the board limits")
    public void robotTriesToMovePastTheBoardLimits() {
        robotTriesToMoveForward();
    }

    @Then("robot does not move past the board limits")
    public void robotDoesNotMovePastTheBoardLimits() {
        assertTrue(this.initialRobotPosition.equals(this.robot.getPosition()));
    }

    @And("robot has orientation {string}")
    public void robotHasOrientation(String arg0) {
        this.robot.setOrientation(Orientation.valueOf(arg0));
    }
}
