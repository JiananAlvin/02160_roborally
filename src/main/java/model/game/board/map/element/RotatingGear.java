package model.game.board.map.element;

import content.TileImageEnum;
import lombok.Data;
import content.OrientationEnum;
import lombok.EqualsAndHashCode;
import model.game.board.map.Position;

@EqualsAndHashCode(callSuper = true)
@Data
public class RotatingGear extends Tile implements Obstacle {
    private boolean rotate;

    public RotatingGear(Integer x, Integer y) {
        super(new Position(x, y));
        this.tileImageEnum = TileImageEnum.ROTATING_GEAR;
    }


    public RotatingGear(Integer x, Integer y, Boolean rotate) {
        super(new Position(x, y));
        setRotate(rotate);
        this.tileImageEnum = TileImageEnum.ROTATING_GEAR;

    }

    public void robotInteraction(Robot r) {
        OrientationEnum orientation;
        if (rotate)
            orientation = OrientationEnum.matchOrientation((r.getOrientation().getAngle() + 90) % 360);
        else
            orientation = OrientationEnum.matchOrientation((r.getOrientation().getAngle() + 270) % 360);
        r.setOrientation(orientation);
    }

    public void setRotate(boolean rotate) {
        this.rotate = rotate;
    }
}
