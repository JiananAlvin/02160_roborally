package gui.game;

import java.awt.*;

import javax.swing.*;

import lombok.Data;
import model.Game;
import model.game.Player;
import model.game.board.map.GameMap;
import model.game.board.map.element.Robot;
import model.game.board.map.element.Tile;

@Data
public class BoardPanel extends JPanel {

    private Tile[][] mapMatrix;
    private int rows;
    private int cols;
    private TilePanel[][] board;

    public BoardPanel() {
        this.mapMatrix = GameMap.getInstance().getContent();
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
        for (Player player : Game.getInstance().getParticipants()) {
            Robot r = player.getRobot();
            board[r.getPosition().getRow()][r.getPosition().getCol()].setRobot(r.getOrientation(), player);
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
