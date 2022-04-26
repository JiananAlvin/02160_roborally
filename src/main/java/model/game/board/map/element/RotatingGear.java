package model.game.board.map.element;

import lombok.Data;
import gui.game.OrientationEnum;
import model.game.board.map.Position;

@Data
public class RotatingGear extends Tile implements Obstacle {

    private Position position;
    private boolean rotate;

    public RotatingGear() {
        this.position = new Position();
    }

    public RotatingGear(Position position) {
        this.position = position;
    }

    public RotatingGear(int x, int y, boolean rotate) {
        this.position = new Position(x, y);
        this.rotate = rotate;
    }

    public void robotInteraction(Robot r){
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
