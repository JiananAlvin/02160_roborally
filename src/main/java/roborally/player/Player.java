package roborally.player;

import model.Robot;

public class Player {
    String name;
    boolean isPlaying;
    boolean hasRobot;
    Robot robot;


    public void setName(String name) {
        this.name = name;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public Player() {
        this.setPlaying(true);
        this.setHasRobot(false);
    }


    public String getName() {
        return this.name;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            Player p = (Player) obj;
            return p.name.equals(this.name);
        }
        return false;
    }

	public boolean hasRobot() {
		return this.hasRobot;
	}

	public void setRobot(Robot robot) {
		
		this.robot = robot;
		
	}

	public Robot getRobot() {
		return this.robot;
	}

	public void setHasRobot(boolean b) {
		
		this.hasRobot= b;
		
	}
}
