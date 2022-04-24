package model.game.board.map.element;

import gui.view.map.TileImageEnum;
import model.game.board.map.Position;

public class WallSouthLaser extends Obstacle{
    public WallSouthLaser(Position position) {
        super(position);
        this.tileImageEnum = TileImageEnum.WALLSOUTHLASER;
    }

    public WallSouthLaser(Integer x, Integer y) {
        super(x, y);
        this.tileImageEnum = TileImageEnum.WALLSOUTHLASER;
    }
}
