package model.game.board.map.element;

import gui.view.map.TileImageEnum;
import model.game.board.map.Position;

public class WallEast extends Tile {
    public WallEast(Position position) {
        super(position);
        this.tileImageEnum = TileImageEnum.WALLEAST;
    }

    public WallEast(Integer x, Integer y) {
        super(x, y);
        this.tileImageEnum = TileImageEnum.WALLEAST;
    }
}
