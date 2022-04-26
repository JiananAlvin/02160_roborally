package model.game.board.map.element;

import content.TileImageEnum;
import model.Game;
import model.game.board.map.Position;

public class Pit extends Tile implements Obstacle{

    public Pit() { super();
        this.tileImageEnum = TileImageEnum.PIT;
    }

    public Pit(Position position) {
        super(position);
        this.tileImageEnum = TileImageEnum.PIT;
    }

    public Pit(Integer x, Integer y) {
        super(x, y);
        this.tileImageEnum = TileImageEnum.PIT;
    }

    public void robotInteraction(Robot r) {
        r.takeDamage(5);
    }
}

