package model.game.board.map.element;

import content.TileImageEnum;
import model.game.board.map.Position;

public class Pit extends Tile implements Interactive {

    public Pit(Position position) {
        super(position);
        this.tileImageEnum = TileImageEnum.PIT;
    }

    public Pit(Integer row, Integer col) {
        this(new Position(row, col));
    }

    public void robotInteraction(Robot r) {
        r.takeDamage(5);
    }
}

