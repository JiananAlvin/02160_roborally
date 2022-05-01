package model.game.board.map.element;

import content.TileImageEnum;
import model.game.board.map.Position;

public class Antenna extends Tile {

    private static Antenna instance;

    public static Antenna getInstance() {
        if (instance == null) {
            instance = new Antenna();
        }
        return instance;
    }

    private Antenna() {
        super(0,0);
        super.tileImageEnum = TileImageEnum.ANTENNA;
    }

}

