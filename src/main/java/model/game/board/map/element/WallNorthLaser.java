package model.game.board.map.element;

import gui.view.map.TileImageEnum;
import model.game.board.map.Position;

public class WallNorthLaser extends Obstacle{
    public WallNorthLaser(Position position) {
        super(position);
        this.tileImageEnum = TileImageEnum.WALLNORTHLASER;
    }

    public WallNorthLaser(Integer x, Integer y) {
        super(x, y);
        this.tileImageEnum = TileImageEnum.WALLNORTHLASER;
    }
}
