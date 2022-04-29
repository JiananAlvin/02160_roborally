package model.game.card;

import content.OrientationEnum;
import model.game.board.map.Position;
import model.game.board.map.element.Robot;
import model.game.card.behaviour.Movement;

public class CardBackUp extends Card {
    public CardBackUp actsOn(Robot robot){
        OrientationEnum robotOrientation = robot.getOrientation();
        Position currentPos = robot.getPosition();
        Position newPos = Movement.calculateNewPosition(robotOrientation, currentPos, -1);
        robot.tryMove(newPos,-1);
        return this;
    }
}
