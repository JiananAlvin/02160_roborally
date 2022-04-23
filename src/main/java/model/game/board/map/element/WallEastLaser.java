package model.game.board.map.element;

import gui.view.map.TileImageEnum;
import model.game.board.map.Position;

public class WallEastLaser extends Obstacle {
    public WallEastLaser(Position position) {
        super(position);
        this.tileImageEnum = TileImageEnum.WALLEASTLASER;
    }

    public WallEastLaser(Integer x, Integer y) {
        super(x, y);
        this.tileImageEnum = TileImageEnum.WALLEASTLASER;
    }


}
