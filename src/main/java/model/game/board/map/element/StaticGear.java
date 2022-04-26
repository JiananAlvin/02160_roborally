package model.game.board.map.element;

import content.TileImageEnum;
import model.game.board.map.Position;

public class StaticGear extends Tile implements Obstacle {
    private int damage = 2;

    public StaticGear() {
        super(new Position());
    }

    public StaticGear(Position position) {
        super(position);
    }

    public StaticGear(Integer x, Integer y) {
        super(x, y);
    }

    public StaticGear(int x, int y) {
        super(new Position(x, y));
    }

    public void robotInteraction(Robot r) {
        r.takeDamage(this.damage);
    }
}
