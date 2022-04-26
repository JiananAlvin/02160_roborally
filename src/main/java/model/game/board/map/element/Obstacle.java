package model.game.board.map.element;

import model.game.board.map.Collision;
import model.game.board.map.Position;

public class Obstacle extends Tile{

    private Position position;
    private int damage = 1;
//    private boolean verticalLaser;
//    private boolean horizonLaser;

    public Obstacle() {
        this.position = new Position();
    }

    public Obstacle(Position position) {
        this.position = position;
    }

    public Obstacle(int x, int y) {
        this.position = new Position(x, y);
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }

    public int getDamage() {
        return damage;
    }

}
