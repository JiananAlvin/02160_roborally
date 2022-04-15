package model.game.board.map.element;

import model.game.board.map.Position;

public class WallSouthLaser extends Obstacle{
    public WallSouthLaser(Position position) {
        super(position);
    }

    public WallSouthLaser(Integer x, Integer y) {
        super(x, y);
    }
}
