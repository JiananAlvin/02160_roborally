package model.game.board.map;


public class Position {
    private int xcoord;
    private int ycoord;

    public Position() {

    }

    public Position(int xcoord, int ycoord) {
        this.xcoord = xcoord;
        this.ycoord = ycoord;
    }

    public int getXcoord() {
        return xcoord;
    }

    public void setXcoord(int xcoord) {
        this.xcoord = xcoord;
    }

    public int getYcoord() {
        return ycoord;
    }

    public void setYcoord(int ycoord) {
        this.ycoord = ycoord;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Position) {
            return ((Position) object).getXcoord() == this.xcoord && ((Position) object).getYcoord() == this.ycoord;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "x:" + this.getXcoord() + "y:" + this.getYcoord();
    }

}
