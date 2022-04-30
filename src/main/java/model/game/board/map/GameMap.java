package model.game.board.map;


import content.MapNameEnum;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import model.game.board.map.element.*;
import utils.MapReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Random;


public class GameMap {

    private static GameMap INSTANCE;

    public static GameMap getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new GameMap();
        }
        return INSTANCE;
    }

    public void removeData() {
        INSTANCE = new GameMap();
    }

    private GameMap() {}

    private String mapName;
    private Tile[][] content;
    private ArrayList<StartPoint> startPoints;
    private ArrayList<RebootPoint> rebootPoints;
    // checkPoints are sorted in numerical order
    private ArrayList<CheckPoint> checkPoints;
    private int height;
    private int width;

    /**
     * GameMap: Initialize an instance of GameMap from the name of MAPNAME.txt
     *
     * @param mapName the name of this map. Such as 'STARTER', 'BEGINNER' represent the map stored in 'STARTER.txt' and 'BEGINNER.txt'.
     */
    @SneakyThrows
    public GameMap init(MapNameEnum mapName) {
        this.mapName = mapName.getMapName();
        this.content = MapReader.txtToTileMatrix(mapName);
        this.height = this.content.length;
        this.width = this.content[0].length;
        this.startPoints = new ArrayList<>();
        this.rebootPoints = new ArrayList<>();
        this.checkPoints = new ArrayList<>();
        for (Tile[] tiles : content)
            for (Tile tile : tiles) {
                if (RebootPoint.class.getName().equals(tile.getClass().getName()))
                    this.rebootPoints.add((RebootPoint) tile);
                else if (StartPoint.class.getName().equals(tile.getClass().getName()))
                    this.startPoints.add((StartPoint) tile);
                else if (CheckPoint.class.getName().equals(tile.getClass().getName()))
                    this.checkPoints.add((CheckPoint) tile);
            }
        this.checkPoints.sort(new Comparator<CheckPoint>() {
            @Override
            public int compare(CheckPoint o1, CheckPoint o2) {
                return o1.getCheckPointNum() - o2.getCheckPointNum();
            }
        });
        return INSTANCE;
    }

    public RebootPoint getARandomRebootPoint() {
        int randomSeed = new Random().nextInt(this.rebootPoints.size());
        return this.rebootPoints.get(randomSeed);
    }

    public Tile getTileAtPosition(Position position) {
        return content[position.getRow()][position.getCol()];
    }

    public String getMapName() {
        return mapName;
    }

    public Tile[][] getContent() {
        return content;
    }

    public ArrayList<StartPoint> getStartPoints() {
        return startPoints;
    }

    public ArrayList<RebootPoint> getRebootPoints() {
        return rebootPoints;
    }

    public ArrayList<CheckPoint> getCheckPoints() {
        return checkPoints;
    }

    public int getHeight() {
        return this.height;
    }

    public int getWidth() {
        return this.width;
    }
}
