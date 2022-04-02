package model.game.board.map.element;

public class Antenna extends Unpassable {

    private static Antenna instance = null;

    private Antenna() {
        super(new Position(0, 4));
    }

    public static Antenna getInstance() {
        if (Antenna.instance == null) {
            Antenna.instance = new Antenna();
            return Antenna.instance;
        } else {
            return Antenna.instance;
        }
    }

    public Position getPosition() {
        return super.getPosition();
    }
}

