package model.game.board.map.element;

import gui.view.map.TileImageEnum;
import model.game.board.map.Position;

public class SouthTwo extends Tile {
    public SouthTwo(Position position) {
        super(position);
        this.tileImageEnum = TileImageEnum.SOUTHTWO;
    }

    public SouthTwo(Integer x, Integer y) {
        super(x, y);
        this.tileImageEnum = TileImageEnum.SOUTHTWO;
    }
}
