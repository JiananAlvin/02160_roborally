package model.game.card.programming;

import model.game.board.map.Orientation;
import model.game.board.map.Position;
import model.game.board.map.element.Robot;
import model.game.card.Card;
import model.game.card.programming.behaviour.Movement;

public class CardMove3 extends Card implements Programmable {
    public void actsOn(Robot robot) {
        Orientation robotOrientation = robot.getOrientation();
        Position currentPos = robot.getPosition();
        Position newPos = Movement.calculateNewPosition(robotOrientation, currentPos, 3);
        robot.setPosition(newPos);
    };
}
