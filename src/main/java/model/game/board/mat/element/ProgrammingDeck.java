package model.game.board.mat.element;

import java.util.ArrayList;

import model.game.card.programming.card.*;
import model.game.Player;
import model.game.card.Card;
import model.game.card.programming.card.*;
import java.util.Random;


public class ProgrammingDeck {

	Player player;
	private ArrayList<Card> cards = new ArrayList<Card>();


	public ProgrammingDeck(Player player) {
		this.player = player;
		this.initialize();
	}
	
	public void initialize() {
		cards.add(new CardAgain());
		cards.add(new CardAgain());
		cards.add(new CardMove1());
		cards.add(new CardMove1());
		cards.add(new CardMove2());
		cards.add(new CardMove2());
		cards.add(new CardMove3());
		cards.add(new CardMove3());
		cards.add(new CardTurnRight());
		cards.add(new CardTurnRight());
		cards.add(new CardTurnLeft());
		cards.add(new CardTurnLeft());
		cards.add(new CardUTurn());
		cards.add(new CardUTurn());
		cards.add(new CardBackUp());
		cards.add(new CardBackUp());
		cards.add(new CardPowerUp());
		cards.add(new CardPowerUp());
	}
	
	public void sendToDiscard(Card c) {
	}

	public ArrayList<Card> getNRandomCards(int i) {
		ArrayList<Card> temp = new ArrayList<Card>();
		Random r = new Random();
		for(int j = 0; j < i; j++) {
			if(cards.size() != 0) {
				int index = r.nextInt(cards.size());
				temp.add(cards.get(index));
				cards.remove(index); 
			} else {
				this.cards = this.player.getDiscard();
			}
		}
		return temp;
	}
}
