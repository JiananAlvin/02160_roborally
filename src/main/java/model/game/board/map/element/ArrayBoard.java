package model.game.board.map.element;

import model.game.card.Card;

import java.util.Objects;

public class ArrayBoard {

    private Object[][] arrayBoard;
    Robot robot;
    int startposx;
    int startposy;

    public  void setStartpos(int x, int y) {
        startposx = x;
        startposy = y;
     }

    public ArrayBoard(Robot robot) {
        this.robot = robot;
        arrayBoard = new Object[][]{{null, null, null}, {null, null, null}, {null, robot, null}};
    }

    public void ReadCard(Card card) {

        arrayBoard[2-1][1] = arrayBoard[2][1];
        arrayBoard[2][1] = null;

        robot.setPosition(1,1);
    }

}
