package model.game.board.map.element;

import content.TileImageEnum;
import lombok.Data;
import content.OrientationEnum;
import model.game.board.map.Position;

@Data
public abstract class Tile {

    private Position position;
    protected TileImageEnum tileImageEnum;
    private OrientationEnum orientation;

    public Tile() {
        this.position = new Position();
    }

    public Tile(Position position) {
        this.position = position;
    }

    public Tile(int x, int y) {
        this.position = new Position(x, y);
    }
}



