package model.game.board.map.element;

import content.TileImageEnum;
import model.game.board.map.Position;

public class Charger extends Tile implements Interactive {

    public Charger(Position position) {
        super(position);
        this.tileImageEnum = TileImageEnum.CHARGER;
    }

    public Charger(Integer row, Integer col) {
        this(new Position(row, col));
    }

    @Override
    public void robotInteraction(Robot r) {
        r.setLives(r.getLives() + 1);
    }
}
