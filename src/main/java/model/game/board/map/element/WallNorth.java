package model.game.board.map.element;

import gui.view.map.TileImageEnum;
import model.game.board.map.Position;

public class WallNorth extends Tile{
    public WallNorth(Position position) {
        super(position);
        this.tileImageEnum = TileImageEnum.WALLNORTH;
    }

    public WallNorth(Integer x, Integer y) {
        super(x, y);
        this.tileImageEnum = TileImageEnum.WALLNORTH;
    }
}
