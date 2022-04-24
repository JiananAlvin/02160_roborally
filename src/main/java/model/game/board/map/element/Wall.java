package model.game.board.map.element;

import gui.view.map.TileImageEnum;
import lombok.Data;
import lombok.EqualsAndHashCode;
import model.game.board.map.Orientation;
import model.game.board.map.Position;

@EqualsAndHashCode(callSuper = true)
@Data
public class Wall extends Tile {
    private Orientation orientation;

    public Wall(Position position, Orientation orientation) {
        super(position);
        this.setOrientationAndTileImage(orientation);
    }

    public Wall(Integer x, Integer y, Orientation orientation) {
        super(x, y);
        this.setOrientationAndTileImage(orientation);
    }

    private void setOrientationAndTileImage(Orientation orientation) {
        switch (orientation) {
            case N:
                this.orientation = Orientation.N;
                this.tileImageEnum = TileImageEnum.WALLNORTH;
                break;
            case S:
                this.orientation = Orientation.S;
                this.tileImageEnum = TileImageEnum.WALLSOUTH;
                break;
            case E:
                this.orientation = Orientation.E;
                this.tileImageEnum = TileImageEnum.WALLEAST;
                break;
            case W:
                this.orientation = Orientation.W;
                this.tileImageEnum = TileImageEnum.WALLWEST;
                break;
            default:
                break;
        }

    }

}
