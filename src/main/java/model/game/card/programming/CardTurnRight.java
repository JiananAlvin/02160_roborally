package model.game.card.programming;

import model.game.board.map.Orientation;
import model.game.board.map.element.Robot;
import model.game.card.Card;

public class CardTurnRight extends Card implements Programmable {
    public void actsOn(Robot r) {
        r.setOrientation(Orientation.matchOrientation((r.getOrientation().getAngle() + 90) % 360));
    }
}
