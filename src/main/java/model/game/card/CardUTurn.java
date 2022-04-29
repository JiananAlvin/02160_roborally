package model.game.card;

import content.OrientationEnum;
import model.game.board.map.element.Robot;
import model.game.card.Card;

public class CardUTurn extends Card {
    public Card actsOn(Robot robot) {
        robot.setOrientation(robot.getOrientation().getOpposite());
        return this;
    }
}
