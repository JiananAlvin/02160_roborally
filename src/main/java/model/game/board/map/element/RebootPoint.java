package model.game.board.map.element;

import model.game.board.map.Position;

public class RebootPoint extends Tile {

    public RebootPoint() {
        super();
    }

    public RebootPoint(Position position) {
        super(position);
    }

    public RebootPoint(Integer x, Integer y) {
        super(x, y);
    }

    public Position getPosition() {
        return super.getPosition();
    }

    public void setPosition(Position position) {
        super.setPosition(position);
    }
}
