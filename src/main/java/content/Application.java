package content;

import gui.GUIAdaptor;
import gui.view.map.CardinalPoints;
import gui.view.map.TileType;
import gui.view.widgets.BoardPanel;
import model.game.board.map.GameMap;
import utils.MapReader;

import javax.swing.*;
import java.awt.*;

/**
 * This class is an entrance for our user application
 */
public class Application {

    private GUIAdaptor adaptor;

    private static Application applicationInstance;

    private Application() {
        this.adaptor = new GUIAdaptor();
    }

    public static Application getApplicationInstance() {
        if (Application.applicationInstance == null)
            Application.applicationInstance = new Application();
        return Application.applicationInstance;
    }

    public boolean run() {
        return Application.applicationInstance != null;
    }

    public static void main(String[] args) {
//        new content.Application().run();

//        TileType[][] mapMatrix = MapReader.txtToTileTypeMatrix("map1");
//        for (int i = 0; i < mapMatrix.length; i++) {
//            for (int j = 0; j < mapMatrix[0].length; j++) {
//                System.out.print("   " + mapMatrix[i][j].getClass());
//            }
//            System.out.println();
//        }

//        int initialRow = 10;
//        int initialColumn = 13;
//
//        CardinalPoints currentDirection = CardinalPoints.N;

        BoardPanel board = new BoardPanel("map1");
//        board.setRobot(initialRow, initialColumn, currentDirection);

//        ControlPanel control = new ControlPanel(board, initialRow, initialColumn, currentDirection);

        JFrame f = new JFrame("RoboRally Group10 - v.0.1");
        f.setLayout(new FlowLayout(FlowLayout.CENTER));
        f.add(board);
//        f.add(control);
        f.setSize(900, 700);
        f.setVisible(true);
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


    }
}
