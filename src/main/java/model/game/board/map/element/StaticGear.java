package model.game.board.map.element;

import model.game.board.map.Position;

public class StaticGear extends Obstacle{

    private int damage = 2;

    public StaticGear(Position position) {
        super(position);
    }

    public StaticGear(Integer x, Integer y) {
        super(x, y);
    }


   public int getDamage(){
        return this.damage;
   }
}
