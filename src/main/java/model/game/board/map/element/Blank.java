package model.game.board.map.element;

import model.game.board.map.Position;

/**
 * @ Class Blank represents nothing on this area
 */
public class Blank extends Tile {


    public Blank(Position position) {
        super(position);
    }

    public Blank(Integer x, Integer y) {
        super(x, y);
    }
}
