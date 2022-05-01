package model.game.board.map;

import lombok.Data;

@Data
public class Position {

    private int row;
    private int col;

    public Position() {}

    public Position(int row, int col) {
        this.row = row;
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
        return "row:" + this.row + ",col:" + this.col;
    }
}
