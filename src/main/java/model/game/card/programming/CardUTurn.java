package model.game.card.programming;

import gui.game.OrientationEnum;
import model.game.board.map.element.Robot;
import model.game.card.Card;

public class CardUTurn extends Card implements Programmable {
    public Card actsOn(Robot robot) {
        robot.setOrientation(OrientationEnum.matchOrientation((robot.getOrientation().getAngle() + 180) % 360));
        return this;

    }
}
