package model.game.board.map.element;

import model.game.board.map.Position;

public class WallSouth extends Tile{
    public WallSouth(Position position) {
        super(position);
    }

    public WallSouth(Integer x, Integer y) {
        super(x, y);
    }
}
