package model.game.board.map.element;

import gui.view.map.TileImageEnum;
import model.game.board.map.Position;

public class EastOne extends Tile {
    public EastOne(Position position) {
        super(position);
        this.tileImageEnum = TileImageEnum.EASTONE;
    }

    public EastOne(Integer x, Integer y) {
        super(x, y);
        this.tileImageEnum = TileImageEnum.EASTONE;
    }
}
