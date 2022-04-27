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
import content.OrientationEnum;
import model.game.board.map.Position;
import model.game.board.map.element.*;
import model.game.card.Card;
import model.game.card.programming.*;
import model.game.card.programming.behaviour.Movement;

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


    //--------------------------------------------------------------------------------------------
    @Given("a robot was facing {string}")
    public void aRobotWasFacing(String arg0) {
        robot = new Robot(RobotNameEnum.HAMMER_BOT);
        robot.setOrientation(OrientationEnum.valueOf(arg0));
    }

    @When("the robot changes its orientation by using the programming card {string}")
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

    @Then("the robot is now facing {string}")
    public void theRobotIsNowFacing(String arg0) {
        assertEquals(OrientationEnum.valueOf(arg0), robot.getOrientation());
    }


    //--------------------------------------------------------------------------------------------


    @Then("the robot now has {string} lives")
    public void the_robot_has_lives(String lives) {
        assertEquals(Integer.parseInt(lives), this.robot.getLives());
    }
    @And("robot has {string} lives")
    public void robotHasLives(String arg0) {
        this.robot.setLives(Integer.parseInt(arg0));
    }


    //--------------------------------------------------------------------------------------------

    @Given("a robot {string}")
    public void aRobot(String arg0) {
        this.robot = new Robot(RobotNameEnum.valueOf(arg0));
    }
    @And("a robot {string} with position {string} {string}")
    public void aRobotWithPosition(String arg0, String arg1, String arg2) {
        this.robot = new Robot(RobotNameEnum.valueOf(arg0));
        this.robot.setInitialPosition(Integer.parseInt(arg1), Integer.parseInt(arg2));
        this.initialRobotPosition = this.robot.getPosition();
        this.game.addParticipant(new Player("test1", this.robot));

        if(this.game.getGameMap()!=null) {
            this.tile = this.game.getGameMap().getTileWithPosition(this.robot.getPosition());
        } else {
            this.tile = new Blank(this.robot.getPosition());
        }
    }
    @And("robot has {string} orientation")
    public void robotHasOrientation(String arg0) {
        this.robot.setOrientation(OrientationEnum.valueOf(arg0));
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

    @And("a position {string} {string} on the map indicating the obstacle of type {string}")
    public void aPositionOnTheMapIndicatingTheObstacle(String xPos, String yPos, String type) {
        switch (type) {
            case "sg":
                this.tile = new StaticGear(Integer.parseInt(xPos), Integer.parseInt(yPos));
                break;
            case "pit":
                this.tile = new Pit(Integer.parseInt(xPos), Integer.parseInt(yPos));
                break;
            case "rgR":
                this.tile = new RotatingGear(Integer.parseInt(xPos), Integer.parseInt(yPos), true);
                break;
            case "rgL":
                this.tile = new RotatingGear(Integer.parseInt(xPos), Integer.parseInt(yPos), false);
                break;
            case "ch":
                this.tile = new Charger(Integer.parseInt(xPos), Integer.parseInt(yPos));
                break;

        }
    }



    //--------------------------------------------------------------------------------------------
    @Then("the robot now has {string} orientation")
    public void theRobotNowHasOrientation(String arg0) {
        assertEquals(OrientationEnum.valueOf(arg0), this.robot.getOrientation());
    }


    @When("robot lands on a static gear")
    public void robotLandsOnAStaticGear() {
//        Move according to the orientation
        Card actionCard = new CardMove1();
        actionCard.actsOn(this.robot);
    }


    @When("robot lands on a rotating gear")
    public void robotLandsOnARotatingGear() {
//        this.robot.tryMove();
        Card actionCard = new CardMove1();
        actionCard.actsOn(this.robot);
    }


    @When("robot lands on a pit")
    public void robotLandsOnAPit() {
//        this.robot.tryMove();
        Card actionCard = new CardMove1();
        actionCard.actsOn(this.robot);
    }

    @Then("robot is sent to the reboot point")
    public void robotIsSentToTheRebootPoint() {
        assertTrue(game.getGameMap().getTileWithPosition(this.robot.getPosition()) instanceof RebootPoint);
    }

    @When("robot lands on a laser tile")
    public void robotLandsOnALaserTile() {
        Card actionCard = new CardMove1();
        actionCard.actsOn(this.robot);
    }



    @Then("robot does not move forward")
    public void robotDoesNotMoveForward() {
        assertEquals(this.robot.getPosition(), this.initialRobotPosition);
    }

    @When("robot tries to move forward and there is a wall")
    public void robotMovesForwardAndThereIsAWall() {
        Card actionCard = new CardMove1();
        actionCard.actsOn(this.robot);
    }

    @When("robot tries to move forward and there is void")
    public void robotTriesToMoveForwardAndThereIsVoid() {
        Card actionCard = new CardMove1();
        actionCard.actsOn(this.robot);
    }


    @When("robot lands on a charger tile")
    public void robotLandsOnAChargerTile() {
        Card actionCard = new CardMove1();
        actionCard.actsOn(this.robot);
    }

    private Robot robot1;
    @When("there is a robot in the position first robot moves on")
    public void thereIsARobotInThePositionFirstRobotMovesOn() {
        Position newPos = Movement.calculateNewPosition(this.robot.getOrientation(), this.robot.getPosition(),1);
        this.robot1 = new Robot("TEST", newPos.getRow(),newPos.getCol());
        this.game.addParticipant(new Player("test1", this.robot1));
        Card actionCard = new CardMove1();
        actionCard.actsOn(this.robot);
        this.initialRobot2Position = newPos;
    }

    @Then("fist robot pushes the second robot")
    public void fistRobotPushesTheSecondRobot() {
        assertEquals(this.robot.getPosition(), this.initialRobot2Position);
        assertEquals(this.robot1.getPosition(), Movement.calculateNewPosition(this.robot.getOrientation(),initialRobot2Position, 1));
    }

    @Then("robot dies")
    public void robotDies() {
        assertEquals(this.robot.getCheckpoints().size(), 0);
        assertEquals(this.robot.getLives(), 5);
    }

    @When("the robot lives are reduced {string} points of damage by the game")
    public void theRobotLivesAreReducedPointsOfDamageByTheGame(String arg0) {
        this.robot.takeDamage(Integer.parseInt(arg0));
    }
}
