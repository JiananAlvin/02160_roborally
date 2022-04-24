package model.game.board.map.element;

import model.game.board.map.Position;

public class Charger extends Tile implements Obstacle{

    public Charger(Position position) {
        super(position);
    }

    public Charger(Integer x, Integer y) {
        super(x, y);
    }

    @Override
    public void robotInteraction(Robot r) {
        r.setLives(r.getLives() + 1);
    }
}
