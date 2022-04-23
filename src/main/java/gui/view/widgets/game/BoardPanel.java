package gui.view.widgets.game;

import java.awt.*;
import java.util.ArrayList;

import javax.swing.*;

import lombok.Data;
import model.Game;
import model.game.Player;
import model.game.board.map.element.Robot;
import model.game.board.map.element.Tile;

@Data
public class BoardPanel extends JPanel {

    private Tile[][] mapMatrix;
    private int rows;
    private int cols;
    private TilePanel[][] board;
    private Game game;

    public BoardPanel(Game game) {
        this.game = game;
        this.mapMatrix = game.getGameMap().getContent();
        this.rows = mapMatrix.length;
        this.cols = mapMatrix[0].length;
        this.board = new TilePanel[rows][cols];

        setLayout(new GridLayout(rows, cols));

        setMinimumSize(new Dimension(cols * TilePanel.PIXEL_SIZE, rows * TilePanel.PIXEL_SIZE));
        setMaximumSize(getMinimumSize());
        setPreferredSize(getMinimumSize());

        loadBoard();
    }

    private void loadBoard() {
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                TilePanel t = new TilePanel(mapMatrix[j][i]);
                board[j][i] = t;
                add(t);
            }
        }
        for (Player player : game.getParticipants()) {
            Robot r = player.getRobot();
            board[r.getPosition().getXcoord()][r.getPosition().getYcoord()].setRobot(r.getOrientation(), player.getUserColor());
        }
    }


//    public static void main(String[] args) {
////        CardinalPoints currentDirection = CardinalPoints.N;
//
//        BoardPanel board = new BoardPanel("map1");
////        board.setRobot(initialRow, initialColumn, currentDirection);
//
////        ControlPanel control = new ControlPanel(board, initialRow, initialColumn, currentDirection);
//
//        JFrame f = new JFrame(Application.APP_TITLE);
//        f.setLayout(new FlowLayout(FlowLayout.CENTER));
//        f.add(board);
////        f.add(control);
//        f.setSize(900, 700);
//        f.setVisible(true);
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    }
}
