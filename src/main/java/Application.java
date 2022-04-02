import gui.GUIAdaptor;

import java.util.ArrayList;
import java.util.TreeMap;

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
////        new Application().run();
//        TileType[][] mapMatrix = MapReader.txt2matrix(new Map("map1"));
//        for (int i = 0; i < mapMatrix.length; i++) {
//            for (int j = 0; j < mapMatrix[0].length; j++) {
//                System.out.print("   " + mapMatrix[i][j].getClass());
//            }
//            System.out.println();
//        }
//    }
//        int initialRow = 10;
//        int initialColumn = 13;
//        CardinalPoints currentDirection = CardinalPoints.N;
//
//        Board board = new Board(new Map("map1"));
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
//
//        int[][] a =  {{2,5}, {6,7}};
//        System.out.println(a[0][0]);
//        System.out.println(a[1][1]);
//
//        ArrayList<Integer> d = new ArrayList<Integer>();
//        int[] c = {-2, -3, -4, -7};
//        for (int i : c){
//            d.add(Math.abs(i));
//        }
//        System.out.println(d.get(0) + d.get(1));

        TreeMap<Integer, String> e = new TreeMap<>();
        e.put(4, "purple");
        e.put(3, "Blue");
        e.put(1, "Red");
        e.put(2, "Green");
        ArrayList<String> g = new ArrayList<>(e.values());
        System.out.println(g);

        ArrayList<Integer> h = new ArrayList<>() {
            {
            add(1);
            add(2);
            }
        };
        int fe = h.remove(0);
        System.out.println(fe);
        System.out.println(h);
    }
}
