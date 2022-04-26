package model.game.card.programming;

import model.game.board.map.element.Robot;
import model.game.card.Card;

public class CardPowerUp extends Card implements Programmable{
    public Card actsOn(Robot robot) {
        robot.setLives(robot.getLives() + 1);
        return this;
    }
}
