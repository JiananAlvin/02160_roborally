package utils;

import gui.view.map.TileType;
import model.game.board.map.Map;
import model.game.board.map.element.Tile;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class MapReader {
    /**
     * @ txt2matrix: input a Map object and then read the txt file of this map
     * then parse the txt file to a 2D array
     */
    public static TileType[][] txt2matrix(Map map) {

        FileReader reader = null;
        BufferedReader readerBuf = null;
        TileType[][] array = null;

        try {
            reader = new FileReader(new File("src/main/resources/maps/" + map.getName() + ".txt"));
            readerBuf = new BufferedReader(reader);
            List<String> strList = new ArrayList<>();

            String lineStr;
            while ((lineStr = readerBuf.readLine()) != null) {
                strList.add(lineStr);
            }
            // The number of rows
            int lineNum = strList.size();
            // The number of columns
            String s = strList.get(0);
            int columnNum = s.split(",").length;

            array = new TileType[strList.size()][columnNum];

            // Put data into a 2D array
            int count = 0;
            for (String str : strList) {
                String[] strs = str.split(",");
                for (int i = 0; i < columnNum; i++) {
                    array[count][i] = TileType.valueOf(strs[i].toUpperCase());
                }
                count++;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {

            try {
                if (reader != null)
                    reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }

            try {
                if (readerBuf != null)
                    readerBuf.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return array;
    }
}
