package model.game.board.map.element;

import content.TileImageEnum;
import lombok.Data;
import model.game.board.map.Position;

@Data
public class Laser extends Tile implements Obstacle{

    private int damage = 1;

    public Laser(Position position, Boolean isVertical) {
        super(position);
        init(isVertical);
    }

    public Laser(Integer x, Integer y, Boolean isVertical) {
        super(x, y);
        init(isVertical);
    }

    private void init(Boolean isVertical) {
        //    if it is true, it is vertical laser
        boolean isVertical1 = isVertical;
        if (isVertical)
            this.tileImageEnum = TileImageEnum.LASERVERTICAL;
        else this.tileImageEnum = TileImageEnum.LASERHORIZONTAL;
    }

    public void robotInteraction(Robot r){
        r.takeDamage(this.damage);
    }
}
