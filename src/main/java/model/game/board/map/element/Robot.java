package model.game.board.map.element;

import lombok.Data;
import model.game.board.map.Orientation;
import model.game.board.map.Position;
import model.game.card.Card;

import java.lang.Math;

@Data
public class Robot {
    private String name;
    private boolean onBoard;
    private Orientation orientation;
    private int lives;
    private Position position;

    public Robot(String name) {
        this.name = name;
        this.onBoard = false;
        this.position = new Position();
    }

    public boolean onBoard() {
        return this.onBoard;
    }

    public String getName() {
        return this.name;
    }

    public void setOnBoard(boolean b) {
        this.onBoard = b;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public void setPosition(int x, int y) {
        this.position.setXcoord(x);
        this.position.setYcoord(y);
    }

    public Position getPosition() {
        return this.position;
    }

    public int distanceToAntenna() {
        return Math.abs(this.position.getXcoord() - Antenna.getInstance().getPosition().getXcoord()) + Math.abs(this.position.getYcoord() - Antenna.getInstance().getPosition().getYcoord());
    }

    @Override
    public String toString() {
        return this.name;
    }

    public void applyCard(Card card) {
        card.action(this);
    }

    public Orientation getOrientation() {
        return this.orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getLives() {
        return this.lives;
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

    public boolean imInsideBoard(int maxX, int maxY){
        if(this.getPosition().getXcoord() > maxX || this.getPosition().getYcoord() > maxY ){return false;}
        else return this.getPosition().getXcoord() >= 0 && this.getPosition().getYcoord() >= 0;
    }
}


