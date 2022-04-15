package model.game.board.map.element;

import model.game.board.map.Position;

public class WallEastLaser extends Obstacle{
    public WallEastLaser(Position position) {
        super(position);
    }

    public WallEastLaser(Integer x, Integer y) {
        super(x, y);
    }
}
