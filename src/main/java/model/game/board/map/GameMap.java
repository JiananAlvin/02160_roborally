package model.game.board.map;

import io.cucumber.java.bs.A;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import model.game.board.map.element.CheckPoint;
import model.game.board.map.element.RebootPoint;
import model.game.board.map.element.StartPoint;
import model.game.board.map.element.Tile;
import utils.MapReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;

@Data
@RequiredArgsConstructor
public class GameMap {

    private String mapName;
    private Tile[][] content;
    private ArrayList<RebootPoint> rebootPoints;
    private ArrayList<StartPoint> startPoints;
    // checkPoints are sorted according to its inner checkPointNum
    private ArrayList<CheckPoint> checkPoints;

    /**
     * GameMap: Initialize an instance of GameMap from the name of Map.txt
     *
     * @param mapName the name of this map. Such as 'map1', 'map2' represent the map stored in 'map1.txt' and 'map2.txt'.
     */
    public GameMap(String mapName) throws IOException {
        this.mapName = mapName;
        this.content = MapReader.txtToTileMatrix(mapName);
        this.rebootPoints = new ArrayList<>();
        this.startPoints = new ArrayList<>();
        this.checkPoints = new ArrayList<>();
        for (Tile[] tiles : content)
            for (Tile tile : tiles) {
                if (RebootPoint.class.getSimpleName().equals(tile.getClass().getSimpleName()))
                    this.rebootPoints.add((RebootPoint) tile);
                else if (StartPoint.class.getSimpleName().equals(tile.getClass().getSimpleName()))
                    this.startPoints.add((StartPoint) tile);
                else if (CheckPoint.class.getSimpleName().equals(tile.getClass().getSimpleName()))
                    this.checkPoints.add((CheckPoint) tile);
            }
        this.checkPoints.sort(new Comparator<CheckPoint>() {
            @Override
            public int compare(CheckPoint o1, CheckPoint o2) {
                return o1.getCheckPointNum() < o2.getCheckPointNum() ? 1 : 0;
            }
        });
    }

    public RebootPoint getRebootPointRandomly() {
        int randomSeed = new Random().nextInt(this.rebootPoints.size());
        return this.rebootPoints.get(randomSeed);
    }



}
