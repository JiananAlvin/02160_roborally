package model.game.board.map.element;

import model.Game;
import model.game.board.map.Position;

public class Pit extends Tile implements Obstacle{

    public Pit() { super();}

    public Pit(Position position) {
        super(position);
    }

    public Pit(Integer x, Integer y) {
        super(x, y);
    }

    public void robotInteraction(Robot r) {
        r.takeDamage(5);
    }
}

