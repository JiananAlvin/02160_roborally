package model.game.board.map.element;

import content.TileImageEnum;
import lombok.Data;
import content.OrientationEnum;
import lombok.EqualsAndHashCode;
import model.game.board.map.Position;

@EqualsAndHashCode(callSuper = true)
@Data
public class RotatingGear extends Tile implements Interactive {
    private boolean isClockwise; // true: clockwise; false: counterclockwise

    public RotatingGear(Position position, Boolean isClockwise) {
        super(position);
        init(isClockwise);
    }

    public RotatingGear(Integer x, Integer y, Boolean isClockwise) {
        super(new Position(x, y));
        init(isClockwise);
    }

    public void robotInteraction(Robot r) {
        OrientationEnum orientation;
        if (this.isClockwise)
            orientation = OrientationEnum.matchOrientation((r.getOrientation().getAngle() + 90) % 360);
        else
            orientation = OrientationEnum.matchOrientation((r.getOrientation().getAngle() + 270) % 360);
        r.setOrientation(orientation);
    }

    private void init(boolean isClockwise) {
        this.isClockwise = isClockwise;
        if (isClockwise)
            this.tileImageEnum = TileImageEnum.ROTATING_GEAR_CLOCKWISE;
        else
            this.tileImageEnum = TileImageEnum.ROTATING_GEAR_COUNTERCLOCKWISE;
    }
}
