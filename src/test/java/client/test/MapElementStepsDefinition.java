package client.test;

import content.MapNameEnum;
import content.RobotNameEnum;
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
    private Position initialRobot2Position;

    @Before
    public void initMapElement() throws IOException {
        this.game = new Game();
        this.game.setGameMap(new GameMap(MapNameEnum.ADVANCED));
    }

    //--------------------------------------------------------------------------------------------
    @Given("a player chose a robot {string}")
    public void aPlayerChoseARobot(String arg0) {
        this.robot = new Robot(RobotNameEnum.valueOf(arg0));
    }

    @Given("there is a game with map {string}")
    public void thereIsAGameWithMap(String arg0) throws IOException {
        this.game = new Game();
        this.game.setGameMap(new GameMap(MapNameEnum.valueOf(arg0)));

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
        robot = new Robot(RobotNameEnum.HAMMER_BOT);
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
        this.robot = new Robot(RobotNameEnum.valueOf(arg0));
        this.robot.setLives(Integer.parseInt(arg1));
    }

    @When("The robot lives are reduced {string} points of damage by the game")
    public void theRobotLivesAreReducedPointsOfDamageByTheGame(String arg0) throws IOException {
        this.game = new Game();
//        this.game.setGameMap(new GameMap(MapNameEnum.STARTER));
//        ArrayList<RebootPoint> rebootPoints = new ArrayList<>();
//        rebootPoints.add(new RebootPoint(1, 1));
//        this.game.getGameMap().setRebootPoints(rebootPoints);
        this.game.setGameMap(new GameMap(MapNameEnum.STARTER));
        this.game.robotTakeDamage(this.robot, Integer.parseInt(arg0));
    }

    @Then("The robot now has {string} lives")
    public void the_robot_has_lives(String lives) {
        assertEquals(Integer.parseInt(lives), this.robot.getLives());
    }


    @Given("a robot {string} had initial position {string} {string} with orientation {string}")
    public void aRobotHadInitialPositionWithOrientation(String robotName, String xPos, String yPos, String orientation) {
        this.robot = new Robot(RobotNameEnum.valueOf(robotName));
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
        card.actsOn(robot);
    }

    @Then("the robot position is {string} {string}")
    public void theRobotPositionIs(String expectedXPos, String expectedYPos) {
        assertEquals(Integer.parseInt(expectedXPos), robot.getPosition().getRow());
        assertEquals(Integer.parseInt(expectedYPos), robot.getPosition().getCol());
    }

    @Given("A robot {string} has position {string} {string}")
    public void aRobotHasPosition(String robotName, String xPos, String yPos) {
        this.robot = new Robot(RobotNameEnum.valueOf(robotName));
        this.robot.setPosition(Integer.parseInt(xPos), Integer.parseInt(yPos));
    }

    @Then("the expected output is {string} in a board that have a maximum size of {string} {string}")
    public void theExpectedOutputIsInABoardThatHaveAMaximumSizeOf(String output, String maxX, String maxY) {
        boolean aux = this.robot.imInsideBoard(Integer.parseInt(maxX), Integer.parseInt(maxY));
        assertEquals(Boolean.valueOf(output), aux);
    }


    //--------------------------------------------------------------------------------------------
    @Given("an antenna and three robots {string}, {string} and {string} chosen by {string}, {string} and {string} respectively")
    public void anAntennaAndThreeRobotsAndChosenByAndRespectively(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
        this.p1 = new Player(arg3, new Robot(RobotNameEnum.valueOf(arg0)));
        this.p2 = new Player(arg4, new Robot(RobotNameEnum.valueOf(arg1)));
        this.p3 = new Player(arg5, new Robot(RobotNameEnum.valueOf(arg2)));
        ArrayList<Player> participants = new ArrayList<>() {
            {
                add(p1);
                add(p2);
                add(p3);
            }
        };
        this.game.setParticipants(participants);
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
    @Given("{string} and {string} are in a game with the map ADVANCED")
    public void andAreInAGameWithTheMapADVANCED(String arg0, String arg1) {
        this.p1 = new Player(arg0, new Robot(RobotNameEnum.SQUASH_BOT));
        this.p2 = new Player(arg1, new Robot(RobotNameEnum.ZOOM_BOT));
        ArrayList<Player> participants = new ArrayList<>() {
            {
                add(p1);
                add(p2);
            }
        };
        this.game.setParticipants(participants);
    }

    @And("playerA's robot has taken checkpoint tokens from all previous checkpoints numerically except {int}")
    public void playeraSRobotHasTakenCheckpointTokensFromAllPreviousCheckpointsNumericallyExceptPoint_number(int arg0) {
        int i = 1;
        for (CheckPoint checkPoint : this.game.getGameMap().getCheckPoints()) {
            if (i < arg0)
                this.p1.getObtainedCheckpointTokens().add(checkPoint);
            i++;
        }
        assertEquals(arg0 - 1, this.p1.getObtainedCheckpointTokens().size());
    }

    @When("playerA's turn ends and his robot stops on the checkpoint {int}")
    public void playeraSTurnEndsAndHisRobotStopsOnTheCheckpointPoint_number(int arg0) {
        this.p1.getRobot().setPosition(this.game.getGameMap().getCheckPoints().get(arg0 - 1).getPosition());
        assertTrue(this.p1.takeToken(this.game.getGameMap().getCheckPoints().get(arg0 - 1)));
    }


    @Then("playerA gets a checkpoint token from this checkpoint successfully and now has {int} checkpoint tokens")
    public void playeraGetsACheckpointTokenFromThisCheckpointSuccessfullyAndNowHasPoint_numberCheckpointTokens(int arg0) {
        assertEquals(arg0, this.p1.getObtainedCheckpointTokens().size());
    }

    @Then("this game checks game status and now the game status is {string}")
    public void thisGameChecksGameStatusAndNowTheGameStatusIs(String arg0) {
        if (this.p1.getObtainedCheckpointTokens().size() == this.game.getGameMap().getCheckPoints().size())
            this.game.setWinner(this.p1);
        if (arg0.equals("finished")) {
            System.out.println(this.p1.getName());
            //System.out.println(this.game.getWinner().getName());
            assertEquals(this.p1, this.game.getWinner());
        } else assertNull(this.game.getWinner());
    }


        //--------------------------------------------------------------------------------------------
//    @And("The robot has initial position {string} {string} with orientation {string}")
//    public void theRobotHasInitialPositionWithOrientation(String xPos, String yPos, String orientation) {
//        this.robot.setInitialPosition(Integer.parseInt(xPos), Integer.parseInt(yPos));
//        this.robot.setOrientation(Orientation.valueOf(orientation));
//    }
//
//
//    @When("robot lands on an obstacle status is true")
//    public void robotLandsOnAnObstacleStatusIsTrue() {
//        this.game.checkCollisionTemporary(this.robot, this.tile);
//    }


    // below is new
    //--------------------------------------------------------------------------------------------
    @And("there is a wall at the same position as the robot")
    public void thereIsAWallAtTheSamePositionAsTheRobot() {
        this.tile = game.getGameMap().getTileWithPosition(this.robot.getPosition());
        if (this.tile.getClass().equals(Wall.class)) {
            System.out.println("wall here");
        }
    }

    @And("the robot faces the wall")
    public void theRobotFacesTheWall() {
        this.robot.setOrientation(this.tile.getOrientation());
    }

//    @When("robot tries to move forward")
//    public void robotTriesToMoveForward() {
//        switch (this.robot.getOrientation()) {
//            case N:
//                this.robot.setPosition(this.robot.getPosition().getRow() - 1, this.robot.getPosition().getCol());
//                break;
//            case S:
//                this.robot.setPosition(this.robot.getPosition().getRow() + 1, this.robot.getPosition().getCol());
//                break;
//            case E:
//                this.robot.setPosition(this.robot.getPosition().getRow(), this.robot.getPosition().getCol() + 1);
//                break;
//            case W:
//                this.robot.setPosition(this.robot.getPosition().getRow(), this.robot.getPosition().getCol() - 1);
//                break;
//
//        }
//    }

//    @Then("robot does not move forward")
//    public void robotDoesNotMoveForward() {
//        assertTrue(this.initialRobotPosition.equals(this.robot.getPosition()));
//    }


    //--------------------------------------------------------------------------------------------
    @And("there is a wall at the next position of the robot")
    public void thereIsAWallAtTheNextPositionOfTheRobot() {
        this.tile = game.getGameMap().getTileWithPosition(this.robot.getPosition());
        if (this.tile.getClass().equals(Wall.class)) {
            System.out.println(this.robot.getName());
            System.out.println("wall next position");
        }
    }


    //--------------------------------------------------------------------------------------------
    private Robot robot2;

    @And("robot faces another robot {string} with position {string} {string}")
    public void robotFacesAnotherRobotWithPosition(String arg0, String arg1, String arg2) {
        this.robot2 = new Robot(arg0, Integer.parseInt(arg1), Integer.parseInt(arg2));
        this.initialRobot2Position = this.robot2.getPosition();
        this.robot2.setOrientation(Orientation.E);
    }

    @When("programming phase is over")
    public void programmingPhaseIsOver() {
        this.game.endProgrammingPhase();
    }

    @Then("robot{int} shoots robot{int}")
    public void robotShootsRobot(int arg0, int arg1) {
        int lives = this.robot2.getLives();
        this.robot.shoot(this.robot2);
        assertTrue(this.robot2.getLives() == lives - 1);
    }


    //--------------------------------------------------------------------------------------------
    @And("robot has orientation {string}")
    public void robotHasOrientation(String arg0) {
        this.robot.setOrientation(Orientation.valueOf(arg0));
    }

//    @When("robot tries to move past the board limits")
//    public void robotTriesToMovePastTheBoardLimits() {
//        robotTriesToMoveForward();
//    }

    @Then("robot does not move past the board limits")
    public void robotDoesNotMovePastTheBoardLimits() {
        assertTrue(this.initialRobotPosition.equals(this.robot.getPosition()));
    }


    //--------------------------------------------------------------------------------------------  not finished
    @Then("robot{int} pushes robot{int}")
    public void robotPushesRobot(int arg0, int arg1) {
        this.robot.push(this.robot2);
    }

    @And("robot{int} is at the initial position of robot{int}")
    public void robotIsAtTheInitialPositionOfRobot(int arg0, int arg1) {
        assertTrue(this.robot.getPosition().equals(this.initialRobot2Position));
    }


    @And("robot{int} is at a new position")
    public void robotIsAtANewPosition(int arg0) {
        assertTrue(!this.robot2.getPosition().equals(this.initialRobot2Position));
    }
}
