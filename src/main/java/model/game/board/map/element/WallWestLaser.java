package model.game.board.map.element;

import model.game.board.map.Position;

public class WallWestLaser extends Obstacle {
    public WallWestLaser(Position position) {
        super(position);
    }

    public WallWestLaser(Integer x, Integer y) {
        super(x, y);
    }
}
