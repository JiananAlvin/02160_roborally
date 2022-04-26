package model.game.board.map.element;

import content.TileImageEnum;
import model.game.board.map.Position;

public class StaticGear extends Obstacle{
    private int damage = 2;

    public StaticGear(Position position) {
        super(position);
//        TODO change it to StaticGear;
        this.tileImageEnum = TileImageEnum.STARTPOINT;
    }

    public StaticGear(Integer x, Integer y) {
        super(x, y);
        this.tileImageEnum = TileImageEnum.STARTPOINT;
    }


   public int getDamage(){
        return this.damage;
   }
}
