package model.game.board.map.element;

import gui.view.map.TileImageEnum;
import model.game.board.map.Position;

public class Charger extends Tile {

    public Charger(Position position) {
        super(position);
        this.tileImageEnum = TileImageEnum.CHARGER;
    }

    public Charger(Integer x, Integer y) {
        super(x, y);
        this.tileImageEnum = TileImageEnum.CHARGER;
    }
}
