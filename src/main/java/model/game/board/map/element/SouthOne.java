package model.game.board.map.element;

import gui.view.map.TileImageEnum;
import model.game.board.map.Position;

public class SouthOne extends Tile{
    public SouthOne(Position position) {
        super(position);
//        TODO Change it to SouthOne
        this.tileImageEnum = TileImageEnum.SOUTHTWO;
    }

    public SouthOne(Integer x, Integer y) {
        super(x, y);
        this.tileImageEnum = TileImageEnum.SOUTHTWO;
    }
}
