package model.game.board.map.element;

import content.TileImageEnum;
import lombok.Data;
import content.OrientationEnum;
import model.game.board.map.Position;

@Data
public class Tile {

    private Position position;
    protected TileImageEnum tileImageEnum;
    public Tile() { this(0, 0); }

    public Tile(Position position) {
        this.position = position;
    }

    public Tile(int row, int col) {
        this(new Position(row, col));
    }
}



