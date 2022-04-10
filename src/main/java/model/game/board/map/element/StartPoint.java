package model.game.board.map.element;

import model.game.board.map.Position;

public class StartPoint extends Tile{
    public StartPoint(Position position) {
        super(position);
    }

    public StartPoint(Integer x, Integer y) {
        super(x, y);
    }
}
