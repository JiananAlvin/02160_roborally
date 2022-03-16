package roborally.player;

import java.util.UUID;

public class Room {
	UUID uuid;
	String roomNumber;
	public Room() {
		this.roomNumber = UUID.randomUUID().toString();
	}
	
	public Room(String roomNumber) {
		this.roomNumber = roomNumber;
	}
	
	
	
	
	
}
