package model.game.board.map.element;

import content.TileImageEnum;
import model.game.board.map.Position;

/**
 * @ Class Blank represents nothing on this area
 */
public class Blank extends Tile {
    public Blank(Position position) {
        super(position);
        this.tileImageEnum = TileImageEnum.BLANK;
    }

    public Blank(Integer x, Integer y) {
        super(x, y);
        this.tileImageEnum = TileImageEnum.BLANK;
    }
}
