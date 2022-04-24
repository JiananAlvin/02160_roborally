package model.game.card.programming.behaviour;

import model.game.board.map.Orientation;
import model.game.board.map.Position;

public class Movement {

    public static Position calculateNewPosition(Orientation robotOrientation, Position currentPos, int amount) {
        Position newPos;

        //this switch takes the robot Orientation given by the Card class, and returns the newPosition based on this orientation
        switch (robotOrientation) {
            case N:
                newPos = new Position(currentPos.getRow() - amount, currentPos.getCol());
                break;
            case S:
                newPos = new Position(currentPos.getRow() + amount, currentPos.getCol());
                break;
            case E:
                newPos = new Position(currentPos.getRow(), currentPos.getCol() + amount);
                break;
            case W:
                newPos = new Position(currentPos.getRow(), currentPos.getCol() - amount);
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + robotOrientation + " is different from N/S/E/W");
        }
        return newPos;
    }
}
