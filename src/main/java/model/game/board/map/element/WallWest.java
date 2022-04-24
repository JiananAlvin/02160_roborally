package model.game.board.map.element;

import gui.view.map.TileImageEnum;
import model.game.board.map.Position;

public class WallWest extends Tile{
    public WallWest(Position position) {
        super(position);
        this.tileImageEnum = TileImageEnum.WALLWEST;
    }

    public WallWest(Integer x, Integer y) {
        super(x, y);
        this.tileImageEnum = TileImageEnum.WALLWEST;
    }
}
