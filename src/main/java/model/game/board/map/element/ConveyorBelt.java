package model.game.board.map.element;

import content.TileImageEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import content.OrientationEnum;
import model.Game;
import model.game.board.map.Position;
import model.game.card.behaviour.Movement;

@EqualsAndHashCode(callSuper = true)
@Data
public class ConveyorBelt extends Tile implements Interactive {

    private OrientationEnum orientation;
    private int distance;

    public ConveyorBelt(Integer row, Integer col, OrientationEnum orientation, Integer distance) {
        super(row, col);
        this.init(orientation, distance);
    }

    public ConveyorBelt(Position position, OrientationEnum orientation, Integer distance) {
        super(position);
        this.init(orientation, distance);
    }

    private void init(OrientationEnum orientation, int distance) {
        this.orientation = orientation;
        this.distance = distance;
        switch (this.orientation) {
            case N:
                if (this.distance == 1) this.tileImageEnum = TileImageEnum.NORTHONE;
                else if (this.distance == 2) this.tileImageEnum = TileImageEnum.NORTHTWO;
                break;
            case S:
                if (this.distance == 1) this.tileImageEnum = TileImageEnum.SOUTHONE;
                else if (this.distance == 2) this.tileImageEnum = TileImageEnum.SOUTHTWO;
                break;
            case W:
                if (this.distance == 1) this.tileImageEnum = TileImageEnum.WESTONE;
                else if (this.distance == 2) this.tileImageEnum = TileImageEnum.WESTTWO;
                break;
            case E:
                if (this.distance == 1) this.tileImageEnum = TileImageEnum.EASTONE;
                else if (this.distance == 2) this.tileImageEnum = TileImageEnum.EASTTWO;
                break;
        }
    }

    public void robotInteraction(Robot r) {
//        System.out.println("belt" + this.orientation.getAngle());
//        System.out.println("ro" + r.getOrientation().getAngle());
//        if (this.orientation.getAngle() == r.getOrientation().getAngle()) {
//            Position newPos = Movement.calculateNewPosition(r.getOrientation(), r.getPosition(), this.distance);
//            if (Game.validateMovement(r, newPos.getRow(), newPos.getCol())) {
//                Robot robotAtPos = Game.getRobotAtPosition(newPos);
//                if (robotAtPos != null) {
//                    // TODO: push!!!!
//                    r.push(robotAtPos);
//                }
//            } else {
//                r.setPosition(newPos);
//            }
//        }
    }
}
