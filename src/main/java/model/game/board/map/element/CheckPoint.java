package model.game.board.map.element;

import gui.view.map.TileImageEnum;
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

    public void setCheckPointNum(int checkPointNum) {
        this.checkPointNum = checkPointNum;
        // TODO add more checkPoint Images
        switch (checkPointNum) {
            case 1:
                this.tileImageEnum = TileImageEnum.CHECKPOINT1;
                break;
            case 2:
                this.tileImageEnum = TileImageEnum.CHECKPOINT1;
                break;
            case 3:
                this.tileImageEnum = TileImageEnum.CHECKPOINT1;
                break;
            default:
                break;
        }
    }
}