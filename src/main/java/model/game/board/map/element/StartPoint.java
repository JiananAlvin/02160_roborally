package model.game.board.map.element;

import content.TileImageEnum;
import model.game.board.map.Position;

public class StartPoint extends Tile {


    public StartPoint(Position position) {
        super(position);
        this.tileImageEnum = TileImageEnum.STARTPOINT;
    }

    public StartPoint(Integer row, Integer col) {
        this(new Position(row, col));
    }

}
