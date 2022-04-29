package model.game.card;

import content.OrientationEnum;
import model.game.board.map.element.Robot;
import model.game.card.Card;

public class CardTurnRight extends Card {
    public Card actsOn(Robot r) {
        r.setOrientation(r.getOrientation().getRight());
        return this;
    }
}
