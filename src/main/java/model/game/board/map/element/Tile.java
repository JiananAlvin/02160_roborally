package model.game.board.map.element;

import gui.view.map.TileImageEnum;
import lombok.Data;
import model.game.board.map.Position;

@Data
public abstract class Tile {

    private Position position;
    protected TileImageEnum tileImageEnum;

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



