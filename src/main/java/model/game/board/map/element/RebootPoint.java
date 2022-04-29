package model.game.board.map.element;

import content.TileImageEnum;
import model.game.board.map.Position;

public class RebootPoint extends Tile {

    public RebootPoint(Position position) {
        super(position);
        this.tileImageEnum = TileImageEnum.REBOOTPOINT;
    }

    public RebootPoint(Integer x, Integer y) {
        super(x, y);
        this.tileImageEnum = TileImageEnum.REBOOTPOINT;
    }
}
