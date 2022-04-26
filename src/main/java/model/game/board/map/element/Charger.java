package model.game.board.map.element;

import content.TileImageEnum;
import model.game.board.map.Position;

public class Charger extends Tile implements Obstacle{

    public Charger(Position position) {
        super(position);
        this.tileImageEnum = TileImageEnum.CHARGER;
    }

    public Charger(Integer x, Integer y) {
        super(x, y);
        this.tileImageEnum = TileImageEnum.CHARGER;
    }

    @Override
    public void robotInteraction(Robot r) {
        r.setLives(r.getLives() + 1);
    }
}
