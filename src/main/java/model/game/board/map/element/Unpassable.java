package model.game.board.map.element;

public abstract class Unpassable extends Tile {

    public Unpassable() {
        super();
    }

    public Unpassable(Position position) {
        super(position);
    }

    public Unpassable(int x, int y) {
        super(x, y);
    }

    public Position getPosition() {
        return super.getPosition();
    }

    public void setPosition(Position position) {
        super.setPosition(position);
    }
}
