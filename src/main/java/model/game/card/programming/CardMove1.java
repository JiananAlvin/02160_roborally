package model.game.card.programming;

import content.OrientationEnum;
import model.game.board.map.Position;
import model.game.board.map.element.Robot;
import model.game.card.Card;
import model.game.card.programming.behaviour.Movement;

public class CardMove1 extends Card implements Programmable {
    public CardMove1 actsOn(Robot robot) {
        OrientationEnum robotOrientation = robot.getOrientation();
        Position currentPos = robot.getPosition();
        //This function gets the orientation, current position and the amount of movement (1, 2, 3, -1...), and gets
        // as a result the new position that the robot should have after playing those cards
        System.out.println(robot.getPosition());
        Position newPos = Movement.calculateNewPosition(robotOrientation, currentPos, 1);
        System.out.println(newPos);
        robot.tryMove(newPos);
        // wait 1 second


        return this;
    }
}
