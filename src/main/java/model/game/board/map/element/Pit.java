package model.game.board.map.element;

import model.game.board.map.Position;

public class Pit extends Tile {
    public Pit() { super();}

    public Pit(Position position) {
        super(position);
    }

    public Pit(Integer x, Integer y) {
        super(x, y);
    }

    public Position getPosition() {
        return super.getPosition();
    }

    public void setPosition(Position position) {
        super.setPosition(position);
    }
}

