package model.game.card.programming.card;

import model.game.board.map.Orientation;
import model.game.board.map.Position;
import model.game.board.map.element.Robot;
import model.game.card.Card;
import model.game.card.programming.behaviour.Movement;

public class CardMove2 extends Card implements Programmable {
    public void action(Robot robot) {
        Orientation robotOrientation = robot.getOrientation();
        Position currentPos = robot.getPosition();
        Position newPos = Movement.calculateNewPosition(robotOrientation, currentPos, 2);
        robot.setPosition(newPos);
    };
}
