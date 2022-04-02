package model.game.board.map.element;

import java.lang.Math;

public class Robot extends Unpassable {

    private String name;
    private boolean onBoard;

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

    public void setPosition(Position position) {
        super.setPosition(position);
    }

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
}

