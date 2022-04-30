package model.game.board.map.element;

import content.TileImageEnum;
import lombok.Data;
import model.game.board.map.Position;

@Data
public class Laser extends Tile implements Interactive {

    private int damage = 1;

    public Laser(Position position, Boolean isVertical) {
        super(position);
        init(isVertical);
    }

    public Laser(Integer row, Integer col, Boolean isVertical) {
        this(new Position(row, col), isVertical);
    }

    private void init(Boolean isVertical) {
        if (isVertical)
            this.tileImageEnum = TileImageEnum.LASERVERTICAL;
        else this.tileImageEnum = TileImageEnum.LASERHORIZONTAL;
    }

    public void robotInteraction(Robot r) {
        r.takeDamage(this.damage);
    }
}
