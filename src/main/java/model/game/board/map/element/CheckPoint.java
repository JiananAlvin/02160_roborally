package model.game.board.map.element;

import lombok.Data;
import model.game.board.map.Position;

@Data
public class CheckPoint extends Tile {
    private int checkPointNum;

    public CheckPoint(Position position) {
        super(position);
    }

    public CheckPoint(Integer x, Integer y) {
        super(x, y);
    }
}