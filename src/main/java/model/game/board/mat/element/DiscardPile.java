package model.game.board.mat.element;

import java.util.ArrayList;

import model.game.Player;
import model.game.card.Card;

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
