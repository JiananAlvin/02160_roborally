package model.game.board.map.element;

/**
 * @ Class Blank represents nothing on this area
 */
public class Blank extends Unpassable {

    public Blank(Position position) {
        super(position);
    }

    public Blank(int x, int y) {
        super(x, y);
    }
}
