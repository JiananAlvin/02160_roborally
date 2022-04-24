package model.game.board.map.element;

import gui.view.map.TileImageEnum;
import model.game.board.map.Position;

public class Laser extends Tile {
    //    if it is true, it is vertical laser
    private boolean isVertical;

    public Laser(Position position, Boolean isVertical) {
        super(position);
        this.isVertical = isVertical;
        if (isVertical)
            this.tileImageEnum = TileImageEnum.LASERVERTICAL;
        else this.tileImageEnum = TileImageEnum.LASERHORIZONTAL;
    }

    public Laser(Integer x, Integer y, Boolean isVertical) {
        super(x, y);
        this.isVertical = isVertical;
        if (isVertical)
            this.tileImageEnum = TileImageEnum.LASERVERTICAL;
        else this.tileImageEnum = TileImageEnum.LASERHORIZONTAL;
    }
}
