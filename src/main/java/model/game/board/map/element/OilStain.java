package model.game.board.map.element;

import content.TileImageEnum;
import model.game.board.map.Position;

public class OilStain extends Tile implements Interactive {
    private int damage = 2;

    public OilStain() {
        super(new Position());
        this.tileImageEnum = TileImageEnum.OIL_STAIN;

    }

    public OilStain(Position position) {
        super(position);
        this.tileImageEnum = TileImageEnum.OIL_STAIN;
    }

    public OilStain(Integer x, Integer y) {
        super(x, y);
        this.tileImageEnum = TileImageEnum.OIL_STAIN;
    }

    public void robotInteraction(Robot r) {
        r.takeDamage(this.damage);
    }
}
