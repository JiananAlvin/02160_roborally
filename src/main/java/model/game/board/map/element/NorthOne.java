package model.game.board.map.element;

import gui.view.map.TileImageEnum;
import model.game.board.map.Position;

public class NorthOne extends Tile{
    public NorthOne(Position position) {
        super(position);
        this.tileImageEnum = TileImageEnum.NORTHONE;
    }

    public NorthOne(Integer x, Integer y) {
        super(x, y);
        this.tileImageEnum = TileImageEnum.NORTHONE;
    }
}
