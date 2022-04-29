package model.game;

import lombok.Data;
import model.game.Player;

import java.util.ArrayList;
import java.util.UUID;

@Data
public class Room {
    private int roomNumber;
    public Room(){};
    public Room(int roomNumber) {
        this.roomNumber = roomNumber;
    }
}
