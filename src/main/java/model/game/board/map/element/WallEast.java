package model.game.board.map.element;

import model.game.board.map.Position;

public class WallEast extends Tile {
    public WallEast(Position position) {
        super(position);
    }

    public WallEast(Integer x, Integer y) {
        super(x, y);
    }
}
