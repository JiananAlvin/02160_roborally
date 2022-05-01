package model.game.board.mat;

import java.util.ArrayList;

import lombok.Data;
import model.game.Player;
import model.game.card.Card;

@Data
//TODO delete the Player player in this class
public class DiscardPile {

	private ArrayList<Card> discards;

	public DiscardPile() {
		this.discards = new ArrayList<>();
	}
}
