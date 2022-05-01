package model.game.card;

import model.game.board.map.element.Robot;

public class CardMove2 extends Card {

    public Card actsOn(Robot robot) {
        return (new CardMove1()).actsOn(robot).actsOn(robot);
    };
}
