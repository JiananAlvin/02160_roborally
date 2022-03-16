package roborally.player;

public class Player {
	String name;
	
	public Player(String name) {
		this.name = name;
	}
	
	public String getName() {
		return this.name;
	}
	
	@Override
	public boolean equals(Object obj) {
		if(obj instanceof Player) {
			Player p = (Player) obj;
			return p.name.equals(this.name);
		}
		return false;
	}
}
