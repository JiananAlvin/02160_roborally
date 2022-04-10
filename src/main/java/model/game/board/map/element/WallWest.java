package model.game.board.map.element;

import model.game.board.map.Position;

public class WallWest extends Tile{
    public WallWest(Position position) {
        super(position);
    }

    public WallWest(Integer x, Integer y) {
        super(x, y);
    }
}
