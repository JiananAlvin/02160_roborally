package model.game.board.map.element;

import content.TileImageEnum;
import model.game.board.map.Position;

public class Antenna extends Tile {

    private static class AntennaSingletonHolder {
        private static Antenna instance = new Antenna();
    }

    private Antenna() {
        super(new Position(0, 4));
        super.tileImageEnum = TileImageEnum.ANTENNA;
    }

    public static Antenna getInstance() {
        return AntennaSingletonHolder.instance;
    }

    public Position getPosition() {
        return super.getPosition();
    }
}

