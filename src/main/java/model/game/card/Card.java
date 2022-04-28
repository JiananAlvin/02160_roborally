package model.game.card;

import model.game.board.map.element.Robot;

/**
 * @ interface: Card
 * There are three kinds of cards in this game:
 * 1. Programming cards(Done)
 * 2. SpecialProgramming cards(Not finished)
 * 3. Upgrade cards(Not finished)
 */
public abstract class Card {
    public abstract Card actsOn(Robot robot);

    @Override
    public String toString() {
        return this.getClass().getSimpleName();
    }
}
