package model;

import model.game.Player;

import java.util.ArrayList;
import java.util.UUID;

public class Room {

    private String roomNumber;
    private ArrayList<Player> players;

    public Room() {
        this.roomNumber = UUID.randomUUID().toString();
    }

    public Room(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
