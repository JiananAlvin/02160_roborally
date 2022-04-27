package model.game.board.mat;

import java.util.ArrayList;

import lombok.Data;
import model.game.Player;
import model.game.card.Card;

@Data
public class DiscardPile {
	private Player player;
	private ArrayList<Card> discards;

	public DiscardPile(Player player) {
		this.player = player;
		this.discards = new ArrayList<>();
	}
}
