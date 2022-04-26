package model.game.board.map.element;

import lombok.Data;
import gui.game.OrientationEnum;
import lombok.EqualsAndHashCode;
import model.game.board.map.Position;

@EqualsAndHashCode(callSuper = true)
@Data
public class RotatingGear extends Tile implements Obstacle {
    private boolean rotate;

    public RotatingGear() {
        super(new Position());
    }

    public RotatingGear(Position position) {
        super(position);
    }

    public RotatingGear(int x, int y, boolean rotate) {
        super(new Position(x, y));
        setRotate(rotate);
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
