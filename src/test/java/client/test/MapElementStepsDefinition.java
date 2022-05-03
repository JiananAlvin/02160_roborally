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
import model.game.card.*;
import model.game.card.behaviour.Movement;
import org.junit.After;

import java.io.IOException;
import java.util.ArrayList;

import static org.junit.Assert.*;

public class MapElementStepsDefinition {
    private Robot robot;
    private Player p1;
    private Player p2;
    private Player p3;
    private Card card;
    private Position initialRobotPosition;
    private Position initialRobot2Position;

    @Before
    public void initMapElement() throws IOException {
//        TODO
//        this.game = new Game();
//        this.game.setGameMap(new GameMap(MapNameEnum.ADVANCED));
    }

    @After
    public void removeData() {
        Game.getInstance().removeData();
        GameMap.getInstance().removeData();
    }

    //1.----------------------------------------------------------------
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
        Game.getInstance().setParticipants(participants);
    }

    @When("robotI, robotII and robotIII are placed in \\({string},{string}), \\({string},{string}),\\({string},{string}) respectively")
    public void robotiRobotIIAndRobotIIIArePlacedInRespectively(String arg0, String arg1, String arg2, String arg3, String arg4, String arg5) {
        this.p1.getRobot().setPosition(Integer.parseInt(arg0), Integer.parseInt(arg1));
        this.p2.getRobot().setPosition(Integer.parseInt(arg2), Integer.parseInt(arg3));
        this.p3.getRobot().setPosition(Integer.parseInt(arg4), Integer.parseInt(arg5));
    }

    @Then("the priority of these players is {string},{string},{string}")
    public void thePriorityOfThesePlayersIs(String arg0, String arg1, String arg2) {
        assertEquals(arg0, Game.getInstance().orderOfPlayers().get(0).getName());
        assertEquals(arg1, Game.getInstance().orderOfPlayers().get(1).getName());
        assertEquals(arg2, Game.getInstance().orderOfPlayers().get(2).getName());
    }


    //2.----------------------------------------------------------------
    @Given("a player chose a robot {string}")
    public void aPlayerChoseARobot(String arg0) {
        this.robot = new Robot(RobotNameEnum.valueOf(arg0));
    }

    @When("the robot gets an initial position randomly")
    public void the_robot_gets_an_initial_position_randomly() {
        this.robot.setPosition(0, 0);
    }

    @Then("robot is now at a position {string} and {string}")
    public void robotIsNowAtAPositionAnd(String arg0, String arg1) {
        assertEquals(new Position(Integer.parseInt(arg0), Integer.parseInt(arg1)), this.robot.getPosition());
    }


    //3.----------------------------------------------------------------
    @Given("a robot was facing {string}")
    public void aRobotWasFacing(String arg0) {
        this.robot = new Robot(RobotNameEnum.HAMMER_BOT);
        this.robot.setOrientation(OrientationEnum.valueOf(arg0));
    }

    @When("perform the programming card {string} on the robot")
    public void performTheProgrammingCardOnTheRobot(String arg0) {
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
        card.actsOn(this.robot);
    }

    @Then("the robot is now facing {string}")
    public void theRobotIsNowFacing(String arg0) {
        assertEquals(OrientationEnum.valueOf(arg0), this.robot.getOrientation());
    }


    //4.----------------------------------------------------------------
    @Given("there is a game with map {string}")
    public void thereIsAGameWithMap(String arg0) throws IOException {
        GameMap.getInstance().init(MapNameEnum.valueOf(arg0));
    }

    @And("a robot {string} with position {string} {string}")
    public void aRobotWithPosition(String arg0, String arg1, String arg2) {
        this.robot = new Robot(RobotNameEnum.valueOf(arg0));
        this.robot.setPosition(Integer.parseInt(arg1), Integer.parseInt(arg2));
        this.initialRobotPosition = this.robot.getPosition();
        Game.getInstance().setParticipants(new ArrayList<>() {
            {
                add(new Player("test1", robot));
            }
        });

        Tile tile;
        if (GameMap.getInstance() != null) {
            tile = GameMap.getInstance().getTileAtPosition(this.robot.getPosition());
        } else {
            tile = new Blank(this.robot.getPosition());
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
    public void theRobotPositionIs(String expectedRow, String expectedCol) {
        Position pos = new Position(Integer.parseInt(expectedRow), Integer.parseInt(expectedCol));
        assertEquals(pos, robot.getPosition());
    }


    //5.----------------------------------------------------------------
    @Given("a robot {string}")
    public void aRobot(String arg0) {
        this.robot = new Robot(RobotNameEnum.valueOf(arg0));
    }

    @And("robot has {string} lives")
    public void robotHasLives(String arg0) {
        this.robot.setLives(Integer.parseInt(arg0));
    }

    @When("robot execute power up card")
    public void robotExecutePowerUpCard() {
        Card actionCard = new CardPowerUp();
        actionCard.actsOn(this.robot);
    }

    @Then("the robot now has {string} lives")
    public void the_robot_has_lives(String lives) {
        assertEquals(Integer.parseInt(lives), this.robot.getLives());
    }


    //6.----------------------------------------------------------------
    @When("the robot lives are reduced {string} points of damage by the game")
    public void theRobotLivesAreReducedPointsOfDamageByTheGame(String arg0) {
        this.robot.takeDamage(Integer.parseInt(arg0));
    }


    //7.----------------------------------------------------------------
    @Given("{string} and {string} are in a game with the map {string}")
    public void andAreInAGameWithTheMapADVANCED(String arg0, String arg1, String arg2) {
        this.p1 = new Player(arg0, new Robot(RobotNameEnum.SQUASH_BOT));
        this.p2 = new Player(arg1, new Robot(RobotNameEnum.ZOOM_BOT));
        ArrayList<Player> participants = new ArrayList<>() {
            {
                add(p1);
                add(p2);
            }
        };
        Game.getInstance().setParticipants(participants);
        GameMap.getInstance().init(MapNameEnum.valueOf(arg2));
    }

    @And("playerA's robot has taken checkpoint tokens from all previous checkpoints numerically except {int}")
    public void playeraSRobotHasTakenCheckpointTokensFromAllPreviousCheckpointsNumericallyExceptPoint_number(int arg0) {
        int i = 1;
        for (CheckPoint checkPoint : GameMap.getInstance().getCheckPoints()) {
            if (i < arg0) {
                this.p1.getRobot().setPosition(checkPoint.getPosition());
                assertTrue(this.p1.getRobot().takeTokens());
            }
            i++;
        }
        assertEquals(arg0 - 1, this.p1.getRobot().getCheckpoints().size());
    }

    @When("playerA's turn ends and his robot stops on the checkpoint {int}")
    public void playeraSTurnEndsAndHisRobotStopsOnTheCheckpointPoint_number(int arg0) {
        this.p1.getRobot().setPosition(GameMap.getInstance().getCheckPoints().get(arg0 - 1).getPosition());
        assertTrue(this.p1.getRobot().takeTokens());
    }

    @Then("playerA gets a checkpoint token from this checkpoint successfully and now has {int} checkpoint tokens")
    public void playeraGetsACheckpointTokenFromThisCheckpointSuccessfullyAndNowHasPoint_numberCheckpointTokens(int arg0) {
        assertEquals(arg0, this.p1.getRobot().getCheckpoints().size());
    }

    @Then("this game checks game status and now the game status is {string}")
    public void thisGameChecksGameStatusAndNowTheGameStatusIs(String arg0) {
        if (arg0.equals("finished")) {
            assertTrue(this.p1.checkWin());
        } else assertFalse(this.p1.checkWin());
    }


    //8.----------------------------------------------------------------
    @When("robot lands on an oil stain")
    public void robotLandsOnAnOilStain() {
        // Move according to the orientation
        Card actionCard = new CardMove1();
        actionCard.actsOn(this.robot);
    }


    //9.----------------------------------------------------------------
    @When("robot lands on a rotating gear with direction {string}")
    public void robotLandsOnARotatingGearWithDirection(String arg0) {
        Card actionCard = new CardMove1();
        actionCard.actsOn(this.robot);
        Tile tile = GameMap.getInstance().getTileAtPosition(this.robot.getPosition());
        if (tile instanceof RotatingGear) {
            switch (arg0) {
                case "clockwise":
                    assertTrue(((RotatingGear) tile).isClockwise());
                    break;
                case "counterclockwise":
                    assertFalse(((RotatingGear) tile).isClockwise());
                    break;
            }
        }

    }


    //10.----------------------------------------------------------------
    @When("robot lands on a pit")
    public void robotLandsOnAPit() {
        // this.robot.tryMove();
        Card actionCard = new CardMove1();
        actionCard.actsOn(this.robot);
    }

    @Then("robot is sent to the reboot point")
    public void robotIsSentToTheRebootPoint() {
        assertTrue(GameMap.getInstance().getTileAtPosition(this.robot.getPosition()) instanceof RebootPoint);
    }


    //11.----------------------------------------------------------------
    @When("robot lands on a laser tile")
    public void robotLandsOnALaserTile() {
        Card actionCard = new CardMove1();
        actionCard.actsOn(this.robot);
    }

    //12.----------------------------------------------------------------
    @When("robot lands on a conveyor belt and move forward {int} steps")
    public void robotLandsOnAConveyorBeltAndMoveForwardSteps(int steps) {
        if (steps == 1)
            new CardMove1().actsOn(this.robot);
        if (steps == 2)
            new CardMove2().actsOn(this.robot);
        if (steps == 3)
            new CardMove3().actsOn(this.robot);
    }

    @Then("robot is on the position {int} and {int}")
    public void robotIsOnThePositionNew_rowAndNew_col(int new_row, int new_col) {
        assertTrue(new_row == this.robot.getPosition().getRow() && new_col == this.robot.getPosition().getCol());
    }

    //13&14.----------------------------------------------------------------
    @When("robot tries to move forward and there is a wall")
    public void robotMovesForwardAndThereIsAWall() {
        Card actionCard = new CardMove1();
        actionCard.actsOn(this.robot);
    }

    @Then("robot does not move forward")
    public void robotDoesNotMoveForward() {
        assertEquals(this.robot.getPosition(), this.initialRobotPosition);
    }

    //15.----------------------------------------------------------------
    @When("robot tries to move backward and there is a wall")
    public void robotTriesToMoveBackwardAndThereIsAWall() {
        Card actionCard = new CardBackUp();
        actionCard.actsOn(this.robot);
    }

    @Then("robot does not move backward")
    public void robotDoesNotMoveBackward() {
        assertEquals(this.robot.getPosition(), this.initialRobotPosition);
    }

    //16.----------------------------------------------------------------
    @When("robot tries to move forward and there is void")
    public void robotTriesToMoveForwardAndThereIsVoid() {
        Card actionCard = new CardMove1();
        actionCard.actsOn(this.robot);
    }

    @Then("robot dies")
    public void robotDies() {
        assertEquals(this.robot.getCheckpoints().size(), 0);
        assertEquals(this.robot.getLives(), 5);
    }


    //17.----------------------------------------------------------------
    @When("robot lands on a charger tile")
    public void robotLandsOnAChargerTile() {
        Card actionCard = new CardMove1();
        actionCard.actsOn(this.robot);
    }


    //18.----------------------------------------------------------------
    private Robot robot1;

    @When("there is a robot in the position first robot moves on")
    public void thereIsARobotInThePositionFirstRobotMovesOn() {
        Position newPos = Movement.calculateNewPosition(this.robot.getOrientation(), this.robot.getPosition(), 1);
        this.robot1 = new Robot(RobotNameEnum.HAMMER_BOT, newPos.getRow(), newPos.getCol());
        Game.getInstance().getParticipants().add(new Player("test", this.robot1));
        Card actionCard = new CardMove1();
        actionCard.actsOn(this.robot);
        this.initialRobot2Position = newPos;
    }

    @Then("fist robot pushes the second robot scenario {int}")
    public void fistRobotPushesTheSecondRobot(int int1) {
        assertEquals(this.robot.getPosition(), this.initialRobot2Position);
        if (int1 == 1) {
            assertEquals(this.robot1.getPosition(), Movement.calculateNewPosition(this.robot.getOrientation(), this.robot.getPosition(), 1));
        } else if (int1 == 2) {
            assertEquals(this.robot1.getPosition(), Movement.calculateNewPosition(this.robot.getOrientation(), this.robot.getPosition(), -1));
        }
    }


    //19.----------------------------------------------------------------
    @When("first robot moves back and there is a robot in the position")
    public void firstRobotMovesBackAndThereIsARobotInThePosition() {
        Position newPos = Movement.calculateNewPosition(this.robot.getOrientation(), this.robot.getPosition(), -1);
        this.robot1 = new Robot(RobotNameEnum.HAMMER_BOT, newPos.getRow(), newPos.getCol());
        Game.getInstance().getParticipants().add(new Player("test", this.robot1));
        Card actionCard = new CardBackUp();
        actionCard.actsOn(this.robot);
        this.initialRobot2Position = newPos;
    }


    //20.----------------------------------------------------------------
    @And("a robot1 {string} with position {int} {int} and orientation {string} and robot2 {string} with position {int} {int} orientation {string} and robot3 {string} with position {int} {int} orientation {string}")
    public void aRobotWithPositionRowColAndOrientation(String arg1, int row, int col, String arg2, String arg3, int row2, int col2, String arg4, String arg5, int row3, int col3, String arg6) {
        Robot robot1 = new Robot(RobotNameEnum.valueOf(arg1), row, col);
        robot1.setOrientation(OrientationEnum.valueOf(arg2));
        Robot robot2 = new Robot(RobotNameEnum.valueOf(arg3), row2, col2);
        robot2.setOrientation(OrientationEnum.valueOf(arg4));
        Robot robot3 = new Robot(RobotNameEnum.valueOf(arg5), row3, col3);
        robot3.setOrientation(OrientationEnum.valueOf(arg6));
        Game.getInstance().setParticipants(new ArrayList<>() {
            {
                add(new Player("Player1", robot1));
                add(new Player("Player2", robot2));
                add(new Player("Player3", robot3));
            }
        });
    }

    @When("shooting phase starts")
    public void shootingPhaseStarts() {
        for (Player player1 : Game.getInstance().getParticipants()) {
            player1.getRobot().shoot(Game.getInstance());
        }
    }

    @Then("robot1 has {int} and robot2 has {int} and robot3 has {int}")
    public void robotHasRobot_livesAndRobotHasRobot_livesAndRobotHasRobot_lives(int arg0, int arg1, int arg2) {
        assertEquals(arg0, Game.getInstance().getParticipants().get(0).getRobot().getLives());
        assertEquals(arg1, Game.getInstance().getParticipants().get(1).getRobot().getLives());
        assertEquals(arg2, Game.getInstance().getParticipants().get(2).getRobot().getLives());
    }
}
