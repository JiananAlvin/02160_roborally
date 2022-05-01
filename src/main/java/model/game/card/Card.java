package model.game.card;

import model.game.board.map.element.Robot;

public abstract class Card {

    public abstract Card actsOn(Robot robot);

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
