package model.game.element.board.map.element;

import game.content.RobotName;
import model.game.element.Player;

public class Robot implements Unpassable {
    Player owner;
    String name;
    int lives;
    Position position;

    public Robot() {
    }

    @Override
    public boolean hasVerticalLaser() {
        return false;
    }

    @Override
    public boolean hasHorizontalLaser() {
        return false;
    }
}
