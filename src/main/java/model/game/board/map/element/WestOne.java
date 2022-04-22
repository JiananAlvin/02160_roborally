package model.game.board.map.element;

import gui.view.map.TileImageEnum;
import model.game.board.map.Position;

public class WestOne extends Tile {
    public WestOne(Position position) {
        super(position);
//        TODO add west 1
        this.tileImageEnum = TileImageEnum.WESTTWO;
    }

    public WestOne(Integer x, Integer y) {
        super(x, y);
        this.tileImageEnum = TileImageEnum.WESTTWO;
    }
}
