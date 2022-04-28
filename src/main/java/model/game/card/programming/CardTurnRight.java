package model.game.card.programming;

import content.OrientationEnum;
import model.game.board.map.element.Robot;
import model.game.card.Card;

public class CardTurnRight extends Card {
    public Card actsOn(Robot r) {
        r.setOrientation(OrientationEnum.matchOrientation((r.getOrientation().getAngle() + 90) % 360));
        return this;
    }
}
