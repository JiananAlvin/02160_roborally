package model;

import model.game.Player;

import java.util.ArrayList;
import java.util.UUID;

public class Room {

    String roomNumber;
    ArrayList<Player> players;

    public Room() {
        this.roomNumber = UUID.randomUUID().toString();
    }

    public Room(String roomNumber) {
        this.roomNumber = roomNumber;
    }
}
