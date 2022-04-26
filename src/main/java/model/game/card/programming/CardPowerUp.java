package model.game.card.programming;

import model.game.board.map.element.Robot;
import model.game.card.Card;

public class CardPowerUp extends Card implements Programmable{
    public void actsOn(Robot robot) {
        robot.setLives(robot.getLives() + 1);
    };
}
