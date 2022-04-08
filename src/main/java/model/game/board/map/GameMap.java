package model.game.board.map;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import model.game.board.map.element.Tile;

@Data
@RequiredArgsConstructor
public class GameMap {

    private String name;
    private Tile[][] content;


}
