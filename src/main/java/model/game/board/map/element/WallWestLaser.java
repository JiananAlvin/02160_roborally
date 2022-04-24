package model.game.board.map.element;

import gui.view.map.TileImageEnum;
import model.game.board.map.Position;

public class WallWestLaser extends Obstacle {
    public WallWestLaser(Position position) {
        super(position);
        this.tileImageEnum = TileImageEnum.WALLWESTLASER;
    }

    public WallWestLaser(Integer x, Integer y) {
        super(x, y);
        this.tileImageEnum = TileImageEnum.WALLWESTLASER;
    }
}
