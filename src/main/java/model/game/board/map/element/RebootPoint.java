package model.game.board.map.element;

public class RebootPoint extends Tile{

    public RebootPoint() {
        super();
    }

    public RebootPoint(Position position) {
        super(position);
    }

    public RebootPoint(int x, int y) {
        super(x, y);
    }

    public Position getPosition() {
        return super.getPosition();
    }

    public void setPosition(Position position) {
        super.setPosition(position);
    }
}
