package model.game.card.behaviour;

import content.OrientationEnum;
import model.game.board.map.GameMap;
import model.game.board.map.Position;
import model.game.board.map.element.Robot;
import model.game.board.map.element.Tile;
import model.game.board.map.element.Wall;

public class Movement {

    public static Position calculateNewPosition(OrientationEnum robotOrientation, Position currentPos, int amount) {
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

    public static boolean validateMovement(Robot r, int row, int col, int movement) {
        // if the robot goes out of the board
        if (!(row >= 0 && row < GameMap.getInstance().getHeight() && col >= 0 && col < GameMap.getInstance().getWidth())) {
            r.takeDamage(5);
            return false;
        }
        // if a wall is on the same tile as robot and blocks the robot from moving
        Tile tile = GameMap.getInstance().getTileAtPosition(r.getPosition());
        if (tile instanceof Wall) { // current position is a wall
            if (movement == 1) {
                return !((Wall) tile).getOrientation().equals(r.getOrientation());
            } else if (movement == -1) {
                return !((Wall) tile).getOrientation().getOpposite().equals(r.getOrientation());
            }
        }
        // if a wall is on the next tile and blocks the robot from moving
        tile = GameMap.getInstance().getTileAtPosition(new Position(row, col));
        if (tile instanceof Wall) {  // next position is wall
            OrientationEnum tileOrientation = ((Wall) tile).getOrientation();
            if (movement == 1) {
                return tileOrientation.equals(r.getOrientation().getLeft()) || tileOrientation.equals(r.getOrientation().getRight())
                        || (tileOrientation.equals(r.getOrientation()));
            } else if (movement == -1) {
                return tileOrientation.equals(r.getOrientation().getLeft()) || tileOrientation.equals(r.getOrientation().getRight())
                        || (tileOrientation.equals(r.getOrientation().getOpposite()));
            }
        }
        return true;
    }
}
