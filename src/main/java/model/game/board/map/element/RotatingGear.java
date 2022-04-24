package model.game.board.map.element;

import model.game.board.map.Orientation;
import model.game.board.map.Position;

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

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }



    public void robotInteraction(Robot r){
        Orientation orientation;
        if (rotate)
            orientation = Orientation.matchOrientation((r.getOrientation().getAngle() + 90) % 360);
        else
            orientation = Orientation.matchOrientation((r.getOrientation().getAngle() + 270) % 360);

        r.setOrientation(orientation);

    }

    public void setRotate(boolean rotate) {
        this.rotate = rotate;
    }

}
