package model.game.board.map.element;

import gui.view.map.TileImageEnum;
import model.game.board.map.Position;

public class WallSouth extends Tile{
    public WallSouth(Position position) {
        super(position);
        this.tileImageEnum = TileImageEnum.WALLSOUTH;
    }

    public WallSouth(Integer x, Integer y) {
        super(x, y);
        this.tileImageEnum = TileImageEnum.WALLSOUTH;
    }
}
