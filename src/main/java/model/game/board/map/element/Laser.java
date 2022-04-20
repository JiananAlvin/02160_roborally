package model.game.board.map.element;

import model.game.board.map.Position;

public class Laser extends Obstacle {
    public Laser(Position position) {
        super(position);
    }

    public Laser(Integer x, Integer y) {
        super(x, y);
    }
}
