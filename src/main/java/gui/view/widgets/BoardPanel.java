package gui.view.widgets;

import java.awt.*;
import java.io.IOException;

import javax.swing.*;

import gui.view.map.CardinalPoints;
import gui.view.map.TileType;
import utils.MapReader;

public class BoardPanel extends JPanel {

    private static final long serialVersionUID = 5384602441603297852L;

    private TileType[][] mapMatrix;
    private int rows;
    private int cols;
    private TilePanel[][] board;

    public BoardPanel(String mapName) {
        try {
            mapMatrix = MapReader.txtToTileTypeMatrix(mapName);
        } catch (IOException e) {
            e.printStackTrace();
        }
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
    }

    public void setRobot(int initialRow, int initialColumn, CardinalPoints currentDirection) {
    }

//    public static void main(String[] args) {
////        CardinalPoints currentDirection = CardinalPoints.N;
//
//        BoardPanel board = new BoardPanel("map1");
////        board.setRobot(initialRow, initialColumn, currentDirection);
//
////        ControlPanel control = new ControlPanel(board, initialRow, initialColumn, currentDirection);
//
//        JFrame f = new JFrame("RoboRally Group10 - v.0.1");
//        f.setLayout(new FlowLayout(FlowLayout.CENTER));
//        f.add(board);
////        f.add(control);
//        f.setSize(900, 700);
//        f.setVisible(true);
//        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//    }
}
