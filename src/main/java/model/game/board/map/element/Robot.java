package model.game.board.map.element;

import content.RobotNameEnum;
import lombok.Data;
import model.Game;
import gui.game.OrientationEnum;
import model.game.board.map.Position;
import model.game.card.Card;

import java.lang.Math;

@Data
public class Robot {
    private String name;
    private boolean onBoard;
    private OrientationEnum orientation;
    private int lives = 5;
    private Position position;

    public Robot(RobotNameEnum robotName) {
        this.name = robotName.getName();
        this.onBoard = false;
        this.position = new Position();
        this.orientation = OrientationEnum.E;
    }

    public Robot(String name, int row, int col) {
        this.name = name;
        this.onBoard = false;
        this.position = new Position(row, col);
        this.orientation = OrientationEnum.E;
    }

    public void setInitialPosition(int row, int col) {
        this.position.setRow(row);
        this.position.setCol(col);
    }

    public void setInitialPosition(Position position) {
        this.position = position;
    }
    public void setPosition(Position position) {
        setPosition(position.getRow(), position.getCol());
    }

    public void setPosition(int row, int col) {
        if (Game.validateMovement(this, row, col)) {
            this.position.setRow(row);
            this.position.setCol(col);
        }

//    public void setPosition(int row, int col) {
//        this.position.setRow(row);
//        this.position.setCol(col);
//    }
    }
    public int distanceToAntenna() {
        return Math.abs(this.position.getRow() - Antenna.getInstance().getPosition().getRow()) + Math.abs(this.position.getCol() - Antenna.getInstance().getPosition().getCol());
    }

    @Override
    public String toString() {
        return this.name;
    }

    public void applyCard(Card card) {
        card.actsOn(this);
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
            return false; //here we reboot
        }
        return true; // here nothing happens
    }

    public boolean imInsideBoard(int maxRow, int maxCol) {
        if (this.getPosition().getRow() > maxRow || this.getPosition().getCol() > maxCol) {
            return false;
        } else return this.getPosition().getRow() >= 0 && this.getPosition().getCol() >= 0;

    }

    public void shoot(Robot robot2) {
//        if (noObstacleToRobot(robot2)) {
            robot2.takeDamage(1);
//        }

    }

    public void push(Robot robot2) {
        this.setPosition(robot2.getPosition());
    }
}


