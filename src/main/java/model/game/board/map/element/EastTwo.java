package model.game.board.map.element;

import gui.view.map.TileImageEnum;
import model.game.board.map.Position;

public class EastTwo extends Tile {
    public EastTwo(Position position) {
        super(position);
        this.tileImageEnum = TileImageEnum.EASTTWO;
    }

    public EastTwo(Integer x, Integer y) {
        super(x, y);
        this.tileImageEnum = TileImageEnum.EASTTWO;
    }
}
