package model.game.board.map;

import model.game.board.map.element.Robot;

public class Collision {
    // we'll difference the different kind of collisions that can happen between the robot and other tiles
    public static final int TWO_ROBOT_COLLISION = 0;
    public static final int ROBOT_OBSTACLE_COLLISION = 1;
    public static final int ROBOT_OUT_OF_BOUNDS_COLLISION = 2;

    //receiving as a parameter a robot and a position, we will return the collision between the robot and tiles in that position
    public Collision checkCollision(Position p, Robot r){
        return new Collision();
    }
}
