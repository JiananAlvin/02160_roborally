package model.game.card.programming;

import model.game.board.map.Orientation;
import model.game.board.map.element.Robot;
import model.game.card.Card;

public class CardUTurn extends Card implements Programmable {
    public void action(Robot robot) {
        robot.setOrientation(Orientation.matchOrientation((robot.getOrientation().getAngle() + 180) % 360));
    }
}
