package model.game.board.map.element;

import gui.view.map.TileImageEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import model.game.board.map.Orientation;
import model.game.board.map.Position;

@EqualsAndHashCode(callSuper = true)
@Data
public class ConveyorBelt extends Tile {
    private Orientation orientation;
    private int distance;

    public ConveyorBelt(Integer row, Integer col, Orientation orientation, Integer distance) {
        super(row, col);
        this.setOrientationAndDistance(orientation, distance);
    }

    public ConveyorBelt(Position position, Orientation orientation, Integer distance) {
        super(position);
        this.setOrientationAndDistance(orientation, distance);
    }

    private void setOrientationAndDistance(Orientation orientation, int distance) {
        this.orientation = orientation;
        this.distance = distance;
        this.setTileImageEnum();
    }

    private void setTileImageEnum() {
        switch (this.orientation) {
            case N:
                if (this.distance == 1) this.tileImageEnum = TileImageEnum.NORTHONE;
                else if (this.distance == 2) this.tileImageEnum = TileImageEnum.NORTHTWO;
                break;
            case S:
                if (this.distance == 1) this.tileImageEnum = TileImageEnum.SOUTHONE;
                else if (this.distance == 2) this.tileImageEnum = TileImageEnum.SOUTHTWO;
                break;
            case W:
                if (this.distance == 1) this.tileImageEnum = TileImageEnum.WESTONE;
                else if (this.distance == 2) this.tileImageEnum = TileImageEnum.WESTTWO;
                break;
            case E:
                if (this.distance == 1) this.tileImageEnum = TileImageEnum.EASTONE;
                else if (this.distance == 2) this.tileImageEnum = TileImageEnum.EASTTWO;
                break;
        }

    }

}
