package GlobalInformationManager;

import java.util.UUID;

import roborally.player.Player;
import roborally.player.Room;

public class InfoManager {
	String username;
	Player user;
//	Later store all
	Room room;
	
	
	public void setUsername(String username) {
		this.username = username;
	}
	
	public void storePlayer(Player p) {
		this.user = p;
	}
	
	public Player getPlayer() {
		return this.user;
	}
	
	public void setRoom(Room room) {
		this.room = room;
	}
	
	public void setRoom(String roomNumber) {
		this.room = new Room(roomNumber);
	}
	
	public Room getRoom() {
		return this.room;
	}
	
	
}
