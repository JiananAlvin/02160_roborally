package gui.view.widgets;

import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.JPanel;

import gui.view.map.CardinalPoints;
import gui.view.map.TileType;
import model.game.board.map.GameMap;
import utils.MapReader;

public class Board extends JPanel {

    private static final long serialVersionUID = 5384602441603297852L;

    private TileType[][] mapMatrix;
    private int rows;
    private int cols;
    private Tile[][] board;

    public Board(GameMap map) {
        mapMatrix = MapReader.txt2matrix(map);
        this.rows = mapMatrix.length;
        this.cols = mapMatrix[0].length;
        this.board = new Tile[rows][cols];

        setLayout(new GridLayout(rows, cols));

        setMinimumSize(new Dimension(cols * Tile.PIXEL_SIZE, rows * Tile.PIXEL_SIZE));
        setMaximumSize(getMinimumSize());
        setPreferredSize(getMinimumSize());

        loadBoard();
    }
//
//    public int getRows() {
//        return rows;
//    }
//
//    public int getColumns() {
//        return cols;
//    }
//
//    public void setRobot(int row, int col, CardinalPoints direction) {
//        board[row][col].setRobot(direction);
//    }
//
//    public void unsetRobot(int row, int col) {
//        board[row][col].unsetRobot();
//    }

    private void loadBoard() {
        for (int j = 0; j < rows; j++) {
            for (int i = 0; i < cols; i++) {
                Tile t = new Tile(mapMatrix[j][i]);
                board[j][i] = t;
                add(t);
            }
        }
    }

    public void setRobot(int initialRow, int initialColumn, CardinalPoints currentDirection) {
    }
}
