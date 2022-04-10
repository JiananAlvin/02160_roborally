package model.game.board.map.element;

import model.game.board.map.Position;

public class WallNorth extends Tile{
    public WallNorth(Position position) {
        super(position);
    }

    public WallNorth(Integer x, Integer y) {
        super(x, y);
    }
}
