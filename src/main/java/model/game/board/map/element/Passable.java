package model.game.board.map.element;

public abstract class Passable extends Tile {

    public Passable(Position position) {
        super(position);
    }

    public Passable(int x, int y) {
        super(x, y);
    }
}
