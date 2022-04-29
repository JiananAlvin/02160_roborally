package model.game.board.map.element;

import content.TileImageEnum;
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
        switch (checkPointNum) {
            case 1:
                this.tileImageEnum = TileImageEnum.CHECKPOINT1;
                break;
            case 2:
                this.tileImageEnum = TileImageEnum.CHECKPOINT2;
                break;
            case 3:
                this.tileImageEnum = TileImageEnum.CHECKPOINT3;
                break;
            default:
                break;
        }
    }

    public void robotInteraction(Robot robot) {
        robot.checkIn(this);
    }
}