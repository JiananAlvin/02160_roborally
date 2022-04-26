package model.game.board.map;


public class Position {
    private int row;
    private int col;

    public Position() {
    }

    public Position(int row, int col) {
        this.row = row;
        this.col = col;
    }

    public int getRow() {
        return row;
    }

    public void setRow(int row) {
        this.row = row;
    }

    public int getCol() {
        return col;
    }

    public void setCol(int col) {
        this.col = col;
    }

    @Override
    public boolean equals(Object object) {
        if (object instanceof Position) {
            return ((Position) object).getRow() == this.row && ((Position) object).getCol() == this.col;
        } else {
            return false;
        }
    }

    @Override
    public String toString() {
        return "[row:" + this.getRow() + "column:" + this.getCol() + "]";
    }

}
