package model.game.element.board.map.element;

import game.content.RobotName;
import model.game.element.Player;

public class Robot implements Unpassable {
    String name;
    boolean onBoard;
    int coordx;
    int coordy;

    public Robot(String name) {

        this.name = name;
        this.onBoard = false;
    }

    public boolean onBoard() {
        return this.onBoard;
    }

    public String getName() {
        return this.name;
    }

    public void setPosition(int i, int j) {
        this.coordx = i;
        this.coordy = j;


    }

    public int getCoordx() {
        return this.coordx;

    }

    public int getCoordy() {
        return this.coordy;

    }

    public void setOnBoard(boolean b) {
        this.onBoard = b;
    }

    @Override
    public boolean hasVerticalLaser() {
        return false;
    }

    @Override
    public boolean hasHorizontalLaser() {
        return false;
    }
}
