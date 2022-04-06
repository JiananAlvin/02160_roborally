package model;

import model.game.Player;

import java.util.ArrayList;
import java.util.UUID;

public class Room {

    private int roomNumber;
    private ArrayList<Player> players;

    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
    }
}
