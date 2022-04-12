package model;

import lombok.Data;
import model.game.Player;

import java.util.ArrayList;
import java.util.UUID;

@Data
public class Room {

    private int roomNumber;
    private ArrayList<Player> players;
    private String mapName;

    public Room(String mapName) {
        this.mapName = mapName;
    }
}
