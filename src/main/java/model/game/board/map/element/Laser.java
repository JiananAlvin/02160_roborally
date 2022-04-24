package model.game.board.map.element;

import model.game.board.map.Position;

public class Laser extends Tile implements Obstacle {
    private int damage = 1;
    private Position position;

    public Laser() {
        this.position = new Position();
    }

    public Laser(Position position) {
        this.position = position;
    }

    public Laser(int x, int y) {
        this.position = new Position(x, y);
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }



    public void robotInteraction(Robot r){
        r.takeDamage(this.damage);
    }
}
