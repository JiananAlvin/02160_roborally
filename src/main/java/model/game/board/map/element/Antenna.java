package model.game.board.map.element;

import gui.view.map.TileImageEnum;
import model.game.board.map.Position;

public class Antenna extends Tile {

    private static Antenna instance = null;

    private Antenna() {
        super(new Position(0, 4));
        super.tileImageEnum = TileImageEnum.ANTENNA;
    }

    public static Antenna getInstance() {
        if (Antenna.instance == null)
            Antenna.instance = new Antenna();
        return Antenna.instance;
    }

    public Position getPosition() {
        return super.getPosition();
    }
}

