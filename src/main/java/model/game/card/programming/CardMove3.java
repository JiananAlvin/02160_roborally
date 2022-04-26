package model.game.card.programming;

import model.game.board.map.element.Robot;
import model.game.card.Card;

public class CardMove3 extends Card implements Programmable {
    public Card actsOn(Robot robot) {
        return (new CardMove1()).actsOn(robot).actsOn(robot).actsOn(robot);
    }
}
