package model;

public class Robot {
	String name;
	boolean onBoard;
	int coordx;
	int coordy;
	
	public Robot(String name) {
		
		this.name = name;
		this.onBoard = false;
	}

	public boolean onBoard() {
		return this.onBoard;
	}

	public String getName() {
		return this.name;
	}

	public void setPosition(int i, int j) {
		this.coordx = i;
		this.coordy = j;
		
		
	}
	
	public int getCoordx() {
		return this.coordx;
		
	}
	
	public int getCoordy() {
		return this.coordy;
		
	}

	public void setOnBoard(boolean b) {
		this.onBoard = b;
	}
}
