package model.game.board.map.element;

import gui.view.map.TileImageEnum;
import model.game.board.map.Position;

public class RebootPoint extends Tile {
    public RebootPoint() {
        super();
        this.tileImageEnum = TileImageEnum.REBOOTPOINT;
    }

    public RebootPoint(Position position) {
        super(position);
        this.tileImageEnum = TileImageEnum.REBOOTPOINT;
    }

    public RebootPoint(Integer x, Integer y) {
        super(x, y);
        this.tileImageEnum = TileImageEnum.REBOOTPOINT;
    }

    public Position getPosition() {
        return super.getPosition();
    }

    public void setPosition(Position position) {
        super.setPosition(position);
    }
}
