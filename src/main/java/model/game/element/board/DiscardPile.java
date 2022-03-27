package model.game.element.board;

import java.util.ArrayList;

import model.game.element.Player;
import model.game.element.card.Card;

public class DiscardPile {
	private ArrayList<Card> discard;
	Player player;
	
	public DiscardPile(Player player) {
		this.player = player;
	}

	public ArrayList<Card> getDiscard() {
		return this.discard;
	}
	
}
