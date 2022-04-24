package model.game.board.map.element;

import model.game.board.map.Position;

public class StaticGear extends Tile implements Obstacle{
    private int damage = 2;
    private Position position;

    public StaticGear() {
        this.position = new Position();
    }

    public StaticGear(Position position) {
        this.position = position;
    }

    public StaticGear(int x, int y) {
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
