package model.game.board.map.element;

import content.RobotNameEnum;
import lombok.Data;
import model.Game;
import content.OrientationEnum;
import model.game.Player;
import model.game.board.map.GameMap;
import model.game.board.map.Position;
import model.game.card.behaviour.Movement;

import java.lang.Math;
import java.util.ArrayList;

@Data
public class Robot {
    private String name;
    private boolean onBoard;
    private OrientationEnum orientation;
    private int lives = 5;
    private Position position;
    private ArrayList<CheckPoint> checkpoints;

    public Robot(RobotNameEnum robotName) {
        this.name = robotName.getName();
        this.onBoard = false;
        this.position = new Position();
        this.orientation = OrientationEnum.E;
    }

    public Robot(RobotNameEnum robotName, int row, int col) {
        this.name = robotName.getName();
        this.onBoard = false;
        this.position = new Position(row, col);
        this.orientation = OrientationEnum.E;
    }


    public void setPosition(int row, int col) {
        this.position = new Position(row, col);
    }

    public boolean tryMove(Position newPos, int movement) {
        if (Game.validateMovement(this, newPos.getRow(), newPos.getCol(), movement)) {
            this.move(newPos, movement);
            return true;
        }
        return false;
    }

    public void move(Position newPos, int movement) {
        Tile t = GameMap.getInstance().getTileWithPosition(newPos);
        Robot robotAtPos = Game.getInstance().getRobotAtPosition(newPos);
        if (robotAtPos != null) {
            this.push(robotAtPos, movement);
        }
        this.position = newPos;
        if (t instanceof Interactive) {
            Interactive o = (Interactive) t;
            o.robotInteraction(this);
        }
    }


    private void push(Robot robotAtPos, int movement) {
        if (movement == 1) {
            robotAtPos.tryMove(Movement.calculateNewPosition(this.getOrientation(), robotAtPos.getPosition(), 1), 1);
        } else if (movement == -1) {
            robotAtPos.tryMove(Movement.calculateNewPosition(this.getOrientation().getOpposite(), robotAtPos.getPosition(), 1), -1);
        }
    }


    public int distanceToAntenna() {
        return Math.abs(this.position.getRow() - Antenna.getInstance().getPosition().getRow()) + Math.abs(this.position.getCol() - Antenna.getInstance().getPosition().getCol());
    }

    @Override
    public String toString() {
        return this.name;
    }

    /**
     * @return: This function returns true if the robot is still alive after taking -some- damage
     */
    public boolean takeDamage(int damage) {
        this.lives -= damage;
        return checkAlive();
    }

    private boolean checkAlive() {
        if (this.lives <= 0) {
            this.reboot();
            return false;
        }
        return true;
    }

    private void reboot() {
        this.position = GameMap.getInstance().getARandomRebootPoint().getPosition();
        this.restoreCheckpoints();
        this.setLives(5);
        // clean register

    }

//    public boolean imInsideBoard(int maxRow, int maxCol) {
//        if (this.getPosition().getRow() > maxRow || this.getPosition().getCol() > maxCol) {
//            return false;
//        } else return this.getPosition().getRow() >= 0 && this.getPosition().getCol() >= 0;
//    }

    public void setLives(int lives) {
        if (lives <= 5)
            this.lives = lives;
    }

    public void checkIn(CheckPoint checkPoint) {
        this.checkpoints.add(checkPoint);
    }

    public void restoreCheckpoints() {
        this.checkpoints = new ArrayList<CheckPoint>();
    }

    public void shoot(Game game) {
        // 1.Check if there is a robot in the robots orientation
        Robot closestRobot = findClosestRobotInFront(this, game);
        // 2.Check if there is a wall between two robots
        if (closestRobot != null && !checkWall(this, closestRobot)) {
            closestRobot.takeDamage(1);
        }
    }

    private Robot findClosestRobotInFront(Robot robot1, Game game) {
        Position position1 = robot1.getPosition(); // the position of the laser origin
        Robot closestRobot = null;
        switch (robot1.getOrientation()) {
            case N: {
                // p1 is facing north
                // ------------------//
                //          p3       //
                //                   //
                //          p2       //
                //          |        //
                //          |        //
                //          p1       //
                //-------------------//
                for (Player player2 : game.getParticipants()) {
                    Robot r2 = player2.getRobot();
                    Position position2 = r2.getPosition();
                    if (position2.getCol() == position1.getCol()
                            && position1.getRow() > position2.getRow())
                        closestRobot = closestRobot == null ? r2 : position2.getRow() > closestRobot.getPosition().getRow() ? r2 : closestRobot;
                }
                break;
            }
            case S: {
                // p1 is facing South
                // ------------------//
                //          p1       //
                //          |        //
                //          p2       //
                //                   //
                //                   //
                //          p3       //
                //-------------------//
                for (Player player2 : game.getParticipants()) {
                    Robot r2 = player2.getRobot();
                    Position position2 = r2.getPosition();
                    if (position2.getCol() == position1.getCol()
                            && position1.getRow() < position2.getRow())
                        closestRobot = closestRobot == null ? r2 : position2.getRow() < closestRobot.getPosition().getRow() ? r2 : closestRobot;
                }
                break;
            }
            case E: {
                // p1 is facing east
                // -----------------------/
                //  p1 ------> p2      p3 /
                //------------------------/
                for (Player player2 : game.getParticipants()) {
                    Robot r2 = player2.getRobot();
                    Position position2 = r2.getPosition();
                    if (position2.getRow() == position1.getRow()
                            && position1.getCol() < position2.getCol())
                        closestRobot = closestRobot == null ? r2 : position2.getCol() < closestRobot.getPosition().getCol() ? r2 : closestRobot;
                }
                break;
            }
            case W: {
                // p1 is facing east
                // -----------------------/
                //  p3         p2<-----p1 /
                //------------------------/
                for (Player player2 : game.getParticipants()) {
                    Robot r2 = player2.getRobot();
                    Position position2 = r2.getPosition();
                    if (position2.getRow() == position1.getRow()
                            && position1.getCol() > position2.getCol())
                        closestRobot = closestRobot == null ? r2 : position2.getCol() > closestRobot.getPosition().getCol() ? r2 : closestRobot;
                }
                break;
            }
        }
        return closestRobot;
    }


    /**
     * @param laserFrom
     * @param laserTo
     * @return if there is wall
     */
    private boolean checkWall(Robot laserFrom, Robot laserTo) {
        Tile[][] mapContent = GameMap.getInstance().getContent();
        switch (laserFrom.getOrientation()) {
            case N:
            case S: {
                int col = laserFrom.getPosition().getCol();
                int rowStart = Math.min(laserFrom.getPosition().getRow(), laserTo.getPosition().getRow());
                int rowEnd = Math.max(laserFrom.getPosition().getRow(), laserTo.getPosition().getRow());
                if (mapContent[rowStart][col].getClass().getSimpleName().equals("Wall")
                        && ((Wall) mapContent[rowStart][col]).getOrientation().equals(OrientationEnum.S))
                    return true;
                for (int i = rowStart + 1; i < rowEnd; i++) {
                    if (mapContent[i][col].getClass().getSimpleName().equals("Wall"))
                        return true;
                }
                if (mapContent[rowEnd][col].getClass().getSimpleName().equals("Wall")
                        && ((Wall) mapContent[rowEnd][col]).getOrientation().equals(OrientationEnum.N))
                    return true;
                break;
            }
            case E:
            case W: {
                int row = laserFrom.getPosition().getRow();
                int colStart = Math.min(laserFrom.getPosition().getCol(), laserTo.getPosition().getCol());
                int colEnd = Math.max(laserFrom.getPosition().getCol(), laserTo.getPosition().getCol());
                if (mapContent[row][colStart].getClass().getSimpleName().equals("Wall")
                        && ((Wall) mapContent[row][colStart]).getOrientation().equals(OrientationEnum.E))
                    return true;
                for (int i = colStart; i <= colEnd; i++) {
                    if (mapContent[row][i].getClass().getSimpleName().equals("Wall"))
                        return true;
                }
                if (mapContent[row][colEnd].getClass().getSimpleName().equals("Wall")
                        && ((Wall) mapContent[row][colEnd]).getOrientation().equals(OrientationEnum.W))
                    return true;
                break;
            }
        }
        return false;
    }
}


