package model.game.board.map.element;

import content.TileImageEnum;
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
        this.init(orientation);
    }

    public Wall(Integer x, Integer y, Orientation orientation) {
        super(x, y);
        this.init(orientation);
    }

    private void init(Orientation orientation) {
        this.orientation = orientation;
        switch (orientation) {
            case N:
                this.tileImageEnum = TileImageEnum.WALLNORTH;
                break;
            case S:
                this.tileImageEnum = TileImageEnum.WALLSOUTH;
                break;
            case E:
                this.tileImageEnum = TileImageEnum.WALLEAST;
                break;
            case W:
                this.tileImageEnum = TileImageEnum.WALLWEST;
                break;
            default:
                break;
        }
    }
}
