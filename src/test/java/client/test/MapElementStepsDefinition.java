package client.test;

import content.RobotName;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.Game;
import model.game.board.map.Orientation;
import model.game.board.map.element.Position;
import model.game.board.map.element.Robot;
import model.game.card.Card;
import model.game.card.programming.card.*;

import static org.junit.Assert.assertEquals;

public class MapElementStepsDefinition {
    private Robot robot;
    private Game game;
    private Robot r1;
    private Robot r2;
    private Robot r3;
    private Card card;

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
    public void theRobotLivesAreReducedPointsOfDamageByTheGame(String arg0) {
        this.game = new Game();
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
        assertEquals(aux, Boolean.valueOf(output));
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


}
