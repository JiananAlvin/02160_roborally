package model.game.board.map.element;

import gui.view.map.TileImageEnum;
import model.game.board.map.Position;

public class WestTwo extends Tile {
    public WestTwo(Position position) {
        super(position);
        this.tileImageEnum = TileImageEnum.WESTTWO;
    }

    public WestTwo(Integer x, Integer y) {
        super(x, y);
        this.tileImageEnum = TileImageEnum.WESTTWO;
    }
}
