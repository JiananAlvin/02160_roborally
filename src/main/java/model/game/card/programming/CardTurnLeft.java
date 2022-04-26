package model.game.card.programming;

import model.game.board.map.element.Robot;
import model.game.card.Card;
import model.game.board.map.Orientation;

public class CardTurnLeft extends Card implements Programmable {
    public void actsOn(Robot robot) {
        robot.setOrientation(Orientation.matchOrientation((robot.getOrientation().getAngle() + 270) % 360));
    }
}
