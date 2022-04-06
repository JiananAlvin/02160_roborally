package model.game.card.programming.behaviour;

import model.game.board.map.Orientation;
import model.game.board.map.element.Position;

public class Movement {

    public static Position calculateNewPosition(Orientation robotOrientation, Position currentPos, int amount) {
        Position newPos;
        switch (robotOrientation) {
            case N:
                newPos = new Position(currentPos.getXcoord(), currentPos.getYcoord() - amount);
                break;
            case S:
                newPos = new Position(currentPos.getXcoord(), currentPos.getYcoord() + amount);
                break;
            case E:
                newPos = new Position(currentPos.getXcoord() + amount, currentPos.getYcoord());
                break;
            case W:
                newPos = new Position(currentPos.getXcoord() - amount, currentPos.getYcoord());
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + robotOrientation + " is different from N/S/E/W");
        }

        return newPos;
    }
}
