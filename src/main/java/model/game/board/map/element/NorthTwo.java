package model.game.board.map.element;

import gui.view.map.TileImageEnum;
import model.game.board.map.Position;

public class NorthTwo extends Tile {
    public NorthTwo(Position position) {
        super(position);
        this.tileImageEnum = TileImageEnum.NORTHTWO;
    }

    public NorthTwo(Integer x, Integer y) {
        super(x, y);
        this.tileImageEnum = TileImageEnum.NORTHTWO;
    }
}
