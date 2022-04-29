package model.game.card;

import model.game.board.map.element.Robot;
import model.game.card.Card;
import content.OrientationEnum;

public class CardTurnLeft extends Card {
    public Card actsOn(Robot robot) {
        robot.setOrientation(robot.getOrientation().getLeft());
        return this;
    }
}
