package model.game.card.programming;

import gui.game.OrientationEnum;
import model.game.board.map.Position;
import model.game.board.map.element.Robot;
import model.game.card.Card;
import model.game.card.programming.behaviour.Movement;

public class CardMove3 extends Card implements Programmable {
    public Card actsOn(Robot robot) {
        return (new CardMove1()).actsOn(robot).actsOn(robot).actsOn(robot);
    }
}
