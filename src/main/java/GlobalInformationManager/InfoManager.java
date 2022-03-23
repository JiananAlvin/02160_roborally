package GlobalInformationManager;

import java.util.UUID;

import model.Game;
import roborally.player.Player;
import roborally.player.Room;

public class InfoManager {
	String username;

	public String getUsername() {
		return username;
	}

	public Player getUser() {
		return user;
	}

	public void setUser(Player user) {
		this.user = user;
	}

	public Game getGame() {
		return game;
	}

	public void setGame(Game game) {
		this.game = game;
	}

	Player user;
//	Later store all
	Room room;
	Game game;
	
	
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
