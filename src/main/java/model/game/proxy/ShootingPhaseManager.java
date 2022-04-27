package model.game.proxy;

import content.OrientationEnum;
import exception.TwoRobotsNotInOneLineException;
import gui.game.GamePanel;
import gui.game.InfoPanel;
import model.Game;
import model.game.Player;
import model.game.board.map.GameMap;
import model.game.board.map.Position;
import model.game.board.map.element.Robot;
import model.game.board.map.element.Tile;
import model.game.board.map.element.Wall;

/**
 * This class is used to manage all robots' shooting after the activation phase
 */
public enum ShootingPhaseManager {
    INSTANCE;

    private Game game;

    public void setupInstance(Game game) {
        this.game = game;
    }

    public void executeRobotsShooting(InfoPanel infoPanel) {
        for (Player player1 : this.game.getParticipants()) {
//            1.Check if there is a robot in the robots orientation
            Robot closestRobot = findClosetRobotInOneLine(player1.getRobot());
//            2.Check if there is a wall between two robots
            if (closestRobot != null && !checkIfThereIsWallBetweenTwoRobots(player1.getRobot(), closestRobot)) {
                closestRobot.takeDamage(1);
                infoPanel.addLogToLogPanel("using laser to hit robot" + closestRobot.getName(), player1);
            }
        }
    }


    public void executeRobotsShooting() {
        for (Player player1 : this.game.getParticipants()) {
//            1.Check if there is a robot in the robots orientation
            Robot closestRobot = findClosetRobotInOneLine(player1.getRobot());
//            2.Check if there is a wall between two robots
            if (closestRobot != null && !checkIfThereIsWallBetweenTwoRobots(player1.getRobot(), closestRobot)) {
                closestRobot.takeDamage(1);
            }
        }
    }

    private Robot findClosetRobotInOneLine(Robot robot1) {
        Position position1 = robot1.getPosition(); // the position of the laser origin
        Robot closetRobot = null;
        switch (robot1.getOrientation()) {
            case N: {
                // p1 is facing north
                // ------------------//
                //          p3       //
                //                   //
                //          p2       //
                //          |        //
                //          |        //
                //          p1       //
                //-------------------//

                for (Player player2 : this.game.getParticipants()) {
                    Robot r2 = player2.getRobot();
                    Position position2 = r2.getPosition();
                    if (position2.getCol() == position1.getCol()
                            && position1.getRow() > position2.getRow())
                        closetRobot = closetRobot == null ? r2 : position2.getRow() > closetRobot.getPosition().getRow() ? r2 : closetRobot;
                }
                break;
            }
            case S: {
                // p1 is facing South
                // ------------------//
                //          p1       //
                //          |        //
                //          p2       //
                //                  //
                //                  //
                //          p3       //
                //-------------------//
                for (Player player2 : this.game.getParticipants()) {
                    Robot r2 = player2.getRobot();
                    Position position2 = r2.getPosition();
                    if (position2.getCol() == position1.getCol()
                            && position1.getRow() < position2.getRow())
                        closetRobot = closetRobot == null ? r2 : position2.getRow() < closetRobot.getPosition().getRow() ? r2 : closetRobot;
                }
                break;
            }
            case E: {
                // p1 is facing east
                // -----------------------/
                //  p1 ------> p2      p3 /
                //------------------------/
                for (Player player2 : this.game.getParticipants()) {
                    Robot r2 = player2.getRobot();
                    Position position2 = r2.getPosition();
                    if (position2.getRow() == position1.getRow()
                            && position1.getCol() < position2.getCol())
                        closetRobot = closetRobot == null ? r2 : position2.getCol() < closetRobot.getPosition().getCol() ? r2 : closetRobot;
                }
                break;
            }
            case W: {
                // p1 is facing east
                // -----------------------/
                //  p3         p2<-----p1 /
                //------------------------/
                for (Player player2 : this.game.getParticipants()) {
                    Robot r2 = player2.getRobot();
                    Position position2 = r2.getPosition();
                    if (position2.getRow() == position1.getRow()
                            && position1.getCol() > position2.getCol())
                        closetRobot = closetRobot == null ? r2 : position2.getCol() > closetRobot.getPosition().getCol() ? r2 : closetRobot;
                }
                break;
            }
        }
        return closetRobot;
    }


    /**
     * @param laserFrom
     * @param laserTo
     * @return if there is wall
     */
    private boolean checkIfThereIsWallBetweenTwoRobots(Robot laserFrom, Robot laserTo) {
        //TODO why it must be a static?????
        Tile[][] mapContent = Game.getGameMap().getContent();
        switch (laserFrom.getOrientation()) {
            case N:
            case S: {
                int col = laserFrom.getPosition().getCol();
                int rowStart = Math.min(laserFrom.getPosition().getRow(), laserTo.getPosition().getRow());
                int rowEnd = Math.max(laserFrom.getPosition().getRow(), laserTo.getPosition().getRow());
                if (mapContent[rowStart][col].getClass().getSimpleName().equals("Wall")
                        &&((Wall)mapContent[rowStart][col]).getOrientation().equals(OrientationEnum.S))
                    return true;
                for (int i = rowStart+1; i < rowEnd; i++) {
                    if (mapContent[i][col].getClass().getSimpleName().equals("Wall"))
                        return true;
                }
                if (mapContent[rowEnd][col].getClass().getSimpleName().equals("Wall")
                        &&((Wall)mapContent[rowEnd][col]).getOrientation().equals(OrientationEnum.N))
                    return true;
                break;
            }
            case E:
            case W: {
                int row = laserFrom.getPosition().getRow();
                int colStart = Math.min(laserFrom.getPosition().getCol(), laserTo.getPosition().getCol());
                int colEnd = Math.max(laserFrom.getPosition().getCol(), laserTo.getPosition().getCol());
                if (mapContent[row][colStart].getClass().getSimpleName().equals("Wall")
                        &&((Wall)mapContent[row][colStart]).getOrientation().equals(OrientationEnum.E))
                    return true;
                for (int i = colStart; i <= colEnd; i++) {
                    if (mapContent[row][i].getClass().getSimpleName().equals("Wall"))
                        return true;
                }
                if (mapContent[row][colEnd].getClass().getSimpleName().equals("Wall")
                        &&((Wall)mapContent[row][colEnd]).getOrientation().equals(OrientationEnum.W))
                    return true;
                break;
            }
        }
        return false;
    }

}
