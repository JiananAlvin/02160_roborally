package model.game.card.programming;

import model.game.board.map.element.Robot;
import model.game.card.Card;
import gui.game.OrientationEnum;

public class CardTurnLeft extends Card implements Programmable {
    public Card actsOn(Robot robot) {
        robot.setOrientation(OrientationEnum.matchOrientation((robot.getOrientation().getAngle() + 270) % 360));
        return this;
    }
}
