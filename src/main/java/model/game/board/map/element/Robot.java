package model.game.board.map.element;

import model.game.board.map.Orientation;
import model.game.card.Card;

import java.lang.Math;
public class Robot extends Unpassable {
    private String name;
    private boolean onBoard;
    private Orientation orientation;
    private int lives;

    public Robot(String name) {
        super();
        this.name = name;
        this.onBoard = false;
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

    public void setPosition(Position position) {super.setPosition(position);    }

    public void setPosition(int x, int y) {
        super.setPosition(x, y);
    }

    public Position getPosition() {
        return super.getPosition();
    }

    public int distanceToAntenna() {
        return Math.abs(super.getPosition().getXcoord() - Antenna.getInstance().getPosition().getXcoord()) + Math.abs(super.getPosition().getYcoord() - Antenna.getInstance().getPosition().getYcoord());
    }

    @Override
    public String toString() {
        return this.name;
    }

    public void applyCard(Card card) {
        card.action(this);
    }

    //-------------------------------------------------------------------------------
    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation) {
        this.orientation = orientation;
    }

    public void setLives(int lives) {
        this.lives = lives;
    }

    public int getLives() {
        return lives;
    }

    public void takeDamage(int damage) {
        this.lives -= damage;
        this.checkLives();
    }

    public boolean imInsideBoard(int maxX, int maxY){
        if(this.getPosition().getXcoord() > maxX || this.getPosition().getYcoord() > maxY ){return false;}
        else if(this.getPosition().getXcoord() < 0 || this.getPosition().getYcoord() < 0){ return false;}

        return true;
    }


    public void reboot() {
        lives = 5;
    }

    private void checkLives() {
        if (lives <= 0) {
            this.reboot();
        }
    }



}

