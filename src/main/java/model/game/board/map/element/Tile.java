package model.game.board.map.element;

import gui.view.map.TileType;

public abstract class Tile {

    private Position position;

    public Tile() {
        this.position = new Position();
    }

    public Tile(Position position) {
        this.position = position;
    }

    public Tile(int x, int y) {
        this.position = new Position(x, y);
    }

    public Position getPosition() {
        return this.position;
    }

    public void setPosition(Position position) {
        this.position = position;
    }
}



