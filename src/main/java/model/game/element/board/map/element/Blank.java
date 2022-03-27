package model.game.element.board.map.element;

/**
 * @ Class Blank represents nothing on this area
 */
public class Blank implements Interactive {

    @Override
    public boolean hasVerticalLaser() {
        return false;
    }

    @Override
    public boolean hasHorizontalLaser() {
        return false;
    }
}
