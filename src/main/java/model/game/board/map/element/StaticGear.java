package model.game.board.map.element;

import content.TileImageEnum;
import model.game.board.map.Position;

public class StaticGear extends Tile implements Obstacle {
    private int damage = 2;

    public StaticGear() {
        super(new Position());
        this.tileImageEnum = TileImageEnum.STATIC_GEAR;

    }

    public StaticGear(Position position) {
        super(position);
        this.tileImageEnum = TileImageEnum.STATIC_GEAR;
    }

    public StaticGear(Integer x, Integer y) {
        super(x, y);
        this.tileImageEnum = TileImageEnum.STATIC_GEAR;
    }


    public void robotInteraction(Robot r) {
        r.takeDamage(this.damage);
    }
}
