package model.game.board.map.element;

import content.RobotNameEnum;
import lombok.Data;
import model.Game;
import content.OrientationEnum;
import model.game.board.map.Position;
import model.game.card.Card;
import model.game.card.programming.CardMove1;
import model.game.card.programming.behaviour.Movement;

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

    public void setInitialPosition(int row, int col) {
        this.position.setRow(row);
        this.position.setCol(col);
    }

    public void setInitialPosition(Position position) {
        this.position = position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public Position getPosition() {
        return this.position;
    }

    public boolean tryMove(Position newPos) {
        if (Game.validateMovement(this, newPos.getRow(), newPos.getCol())) {
            Tile t = Game.getGameMap().getTileWithPosition(newPos);
            this.move(newPos, t);
            return true;
        }
        return false;
    }

    public void move(Position newPos, Tile t) {
        Robot robotAtPos = Game.getRobotAtPosition(newPos);
        if (robotAtPos != null) {
            this.push(robotAtPos);
        }
        this.position = newPos;
        if (t instanceof Obstacle) {
            Obstacle o = (Obstacle) t;
            o.robotInteraction(this);
        }

    }

    private void push(Robot robotAtPos) {
        robotAtPos.tryMove(Movement.calculateNewPosition(this.getOrientation(), robotAtPos.getPosition(), 1));
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
            this.reboot();
            return false;
        }
        return true;
    }

    private void reboot() {
        this.position = Game.getGameMap().getARandomRebootPoint().getPosition();
        this.restoreCheckpoints();
        this.setLives(5);
        // clean register

    }

    public boolean imInsideBoard(int maxRow, int maxCol) {
        if (this.getPosition().getRow() > maxRow || this.getPosition().getCol() > maxCol) {
            return false;
        } else return this.getPosition().getRow() >= 0 && this.getPosition().getCol() >= 0;

    }

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

    public void robotInteraction(Robot robot) {
        OrientationEnum initialOrientation = this.orientation;
        this.orientation = robot.orientation;
        Card c = new CardMove1();
        c.actsOn(this);
        this.orientation = initialOrientation;
    }

}


