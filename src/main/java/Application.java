import gui.GUIAdaptor;
import gui.view.map.TileType;
import model.game.board.map.Map;
import utils.MapReader;

import java.util.ArrayList;

/**
 * This class is an entrance for our user application
 */
public class Application {

    GUIAdaptor adaptor;

    public Application() {
        this.adaptor = new GUIAdaptor();
    }

    public void run() {

    }

    public static void main(String[] args) {
//        new Application().run();
        TileType[][] mapMatrix = MapReader.txt2matrix(new Map("map1"));
        for (int i = 0; i < mapMatrix.length; i++) {
            for (int j = 0; j < mapMatrix[0].length; j++) {
                System.out.print("   " + mapMatrix[i][j].getClass());
            }
            System.out.println();
        }
    }
}
