package model.game.board.map.element;

import model.game.board.map.Position;

public class WallNorthLaser extends Obstacle {
    public WallNorthLaser(Position position) {
        super(position);
    }

    public WallNorthLaser(Integer x, Integer y) {
        super(x, y);
    }
}