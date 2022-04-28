package model.game.proxy;

import content.OrientationEnum;
import gui.game.InfoPanel;
import model.Game;
import model.game.Player;
import model.game.board.map.Position;
import model.game.board.map.element.Robot;
import model.game.board.map.element.Tile;
import model.game.board.map.element.Wall;

/**
 * This class is used to manage all robots' shooting after the activation phase
 */
public enum PhaseManager {

    INSTANCE;

    public void executeRobotsShooting(Game game) {
        for (Player player1 : game.getParticipants()) {
            player1.getRobot().shoot(game);
        }
    }
}
