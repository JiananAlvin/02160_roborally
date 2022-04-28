package model.game.card.programming;

import model.game.board.map.element.Robot;
import model.game.card.Card;
import content.OrientationEnum;

public class CardTurnLeft extends Card {
    public Card actsOn(Robot robot) {
        robot.setOrientation(OrientationEnum.matchOrientation((robot.getOrientation().getAngle() + 270) % 360));
        return this;
    }
}
