package utils;

import content.MapName;
import gui.view.map.TileImageEnum;
import model.game.board.map.Orientation;
import model.game.board.map.element.*;

import java.io.*;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Map;

/**
 *
 */
public class MapReader {

    /**
     * @ PATH_TO_MAP_FILE: The path to the folder storing maps.txt
     */
    private static final String PATH_TO_MAP_FILE = "src/main/resources/maps/";
    private static final String FULLY_QUALIFIED_NAME_OF_ELEMENT = "model.game.board.map.element.";
    private static final String FULLY_QUALIFIED_NAME_OF_CHECKPOINT = "model.game.board.map.element.CheckPoint";
    private static final String FULLY_QUALIFIED_NAME_OF_CONVEYORBELT = "model.game.board.map.element.ConveyorBelt";
    private static final String FULLY_QUALIFIED_NAME_OF_WALL = "model.game.board.map.element.Wall";
    private static final String FULLY_QUALIFIED_NAME_OF_LASER = "model.game.board.map.element.Laser";


    /**
     * @ readLineToStringArray: Read the content of .txt file to a string array.
     * One line of this txt file -> one string element of this arraylist
     */
    private static ArrayList<String> readLineToStringArray(MapName mapName) throws IOException {
        ArrayList<String> strList = new ArrayList<>();
        FileReader reader = new FileReader(new File(PATH_TO_MAP_FILE + mapName.getMapName() + ".txt"));
        BufferedReader readerBuf = new BufferedReader(reader);
        String lineStr;
        while ((lineStr = readerBuf.readLine()) != null) {
            strList.add(lineStr);
        }
        reader.close();
        readerBuf.close();
        return strList;
    }

    /**
     * @ readLineToStringArray: Read the content of .txt file to a string array.
     * One line of this txt file -> one string element of this arraylist
     */
    private static Tile createTileInstance(String str, int row, int col) {
        if ("Antenna".equals(str)) return Antenna.getInstance();
        try {
            if (str.startsWith("CheckPoint")) {
                Class<?> clz = Class.forName(FULLY_QUALIFIED_NAME_OF_CHECKPOINT);
                CheckPoint checkPoint = (CheckPoint) clz.getDeclaredConstructor(Integer.class, Integer.class).newInstance(row, col);
                checkPoint.setCheckPointNum(Integer.parseInt("" + str.charAt(str.length() - 1)));
                return checkPoint;
            } else if (str.equals("EastOne")) {
                return createConveyorBelt(row, col, Orientation.E, 1);
            } else if (str.equals("EastTwo")) {
                return createConveyorBelt(row, col, Orientation.E, 2);
            } else if (str.equals("WestOne")) {
                return createConveyorBelt(row, col, Orientation.W, 1);
            } else if (str.equals("WestTwo")) {
                return createConveyorBelt(row, col, Orientation.W, 2);
            } else if (str.equals("NorthOne")) {
                return createConveyorBelt(row, col, Orientation.N, 1);
            } else if (str.equals("NorthTwo")) {
                return createConveyorBelt(row, col, Orientation.N, 2);
            } else if (str.equals("SouthOne")) {
                return createConveyorBelt(row, col, Orientation.S, 1);
            } else if (str.equals("SouthTwo")) {
                return createConveyorBelt(row, col, Orientation.S, 2);
            } else if (str.equals("WallEast")) {
                return createWall(row, col, Orientation.E);
            } else if (str.equals("WallNorth")) {
                return createWall(row, col, Orientation.N);
            } else if (str.equals("WallWest")) {
                return createWall(row, col, Orientation.W);
            } else if (str.equals("WallSouth")) {
                return createWall(row, col, Orientation.S);
            } else if (str.equals("LaserVertical")) {
                return createLaser(row, col, true);
            } else if (str.equals("LaserHorizontal")) {
                return createLaser(row, col, false);
            } else {
                Class<?> clz = Class.forName(FULLY_QUALIFIED_NAME_OF_ELEMENT + str);
                return (Tile) clz.getDeclaredConstructor(Integer.class, Integer.class).newInstance(row, col);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    private static Laser createLaser(int row, int col, boolean isVertical) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clz = Class.forName(FULLY_QUALIFIED_NAME_OF_LASER);
        Laser laser = (Laser) clz.getDeclaredConstructor(Integer.class, Integer.class, Boolean.class).newInstance(row, col, isVertical);
        return laser;
    }

    private static ConveyorBelt createConveyorBelt(int row, int col, Orientation orientation, int distance) throws ClassNotFoundException, NoSuchMethodException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clz = Class.forName(FULLY_QUALIFIED_NAME_OF_CONVEYORBELT);
        ConveyorBelt conveyorBelt = (ConveyorBelt) clz.getDeclaredConstructor(Integer.class, Integer.class, Orientation.class, Integer.class).newInstance(row, col, orientation, distance);
        return conveyorBelt;
    }

    private static Wall createWall(int row, int col, Orientation orientation) throws NoSuchMethodException, ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        Class<?> clz = Class.forName(FULLY_QUALIFIED_NAME_OF_WALL);
        Wall wall = (Wall) clz.getDeclaredConstructor(Integer.class, Integer.class, Orientation.class).newInstance(row, col, orientation);
        return wall;
    }

    /**
     * @ txtToTileMatrix: input a Map name (like "map1", "map2") and then read the txt file of this map
     * then parse the txt file to a 2D array of Tile
     */
    public static Tile[][] txtToTileMatrix(MapName mapName) throws IOException {
        Tile[][] result = null;
        ArrayList<String> strList = MapReader.readLineToStringArray(mapName);

        // The number of columns
        String s = strList.get(0);
        int columnNum = s.split(",").length;

        result = new Tile[strList.size()][columnNum];

        // Put data into a 2D array
        int count = 0;
        for (String line : strList) {
            String[] strs = line.split(",");
            for (int i = 0; i < columnNum; i++) {
                result[count][i] = MapReader.createTileInstance(strs[i], count, i);
            }
            count++;
        }
        return result;
    }
}
