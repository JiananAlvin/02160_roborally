package model.game;

import java.awt.*;
import java.util.ArrayList;

import lombok.Data;
import model.game.board.map.GameMap;
import model.game.board.map.element.CheckPoint;
import model.game.board.map.element.Tile;
import model.game.board.mat.ProgrammingDeck;
import model.game.board.mat.DiscardPile;
import model.game.board.map.element.Robot;
import model.game.board.mat.RegisterArea;
import model.game.card.Card;

@Data
public class Player {

    private String name;
    private Robot robot;
    private ProgrammingDeck programmingDeck;
    private DiscardPile discardPile;
    private RegisterArea registerArea;
    private ArrayList<Card> cardsInHand;
    private Color playerColor;


    public Player(String name, Robot robot) {
        this.name = name;
        this.robot = robot;
        this.programmingDeck = new ProgrammingDeck();
        this.discardPile = new DiscardPile();
        this.registerArea = new RegisterArea();
    }

    public Player() {
        this.programmingDeck = new ProgrammingDeck();
        this.discardPile = new DiscardPile();
        this.registerArea = new RegisterArea();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj instanceof Player) {
            Player p = (Player) obj;
            return p.name.equals(this.name);
        }
        return false;
    }

    public boolean checkWin() {
        return this.robot.getCheckpoints().size() == GameMap.getInstance().getCheckPoints().size();
    }

    /**
     * In each round, A player draws 9 cards from his programming deck. If there are fewer than 9 to draw from, he should take
     * what is available. Then shuffles the discard pile to replenish his programming deck, and draws until he has nine cards.
     */
    public void drawCards() {
        this.cardsInHand = new ArrayList<>();
        if (this.programmingDeck.getCards().size() < ProgrammingDeck.NUMBER_OF_CARDS_DRAWN_IN_EACH_ROUND) {
            // if there are fewer than 9 cards left in Programming Deck
            // fetch all the cards left
            this.cardsInHand = new ArrayList<>(this.programmingDeck.getCards());
            this.programmingDeck.getCards().removeAll(this.programmingDeck.getCards());
            this.replenishProgrammingDeck();
            ArrayList<Card> complements = new ArrayList<>(this.programmingDeck.getCards().subList(0, ProgrammingDeck.NUMBER_OF_CARDS_DRAWN_IN_EACH_ROUND - this.cardsInHand.size()));
            this.programmingDeck.getCards().subList(0, ProgrammingDeck.NUMBER_OF_CARDS_DRAWN_IN_EACH_ROUND - this.cardsInHand.size()).clear();
            this.cardsInHand.addAll(complements);
        } else {
            this.cardsInHand = new ArrayList<>(this.programmingDeck.getCards().subList(0, ProgrammingDeck.NUMBER_OF_CARDS_DRAWN_IN_EACH_ROUND));
            this.programmingDeck.getCards().subList(0, ProgrammingDeck.NUMBER_OF_CARDS_DRAWN_IN_EACH_ROUND).clear();
        }
    }

    /**
     * <p>This method replenishes a player's programming deck with all cards in his discard pile, and then shuffles the
     * programming deck.
     */
    private void replenishProgrammingDeck() {
        this.programmingDeck.getCards().addAll(this.discardPile.getDiscards());
        this.discardPile.getDiscards().removeAll(this.discardPile.getDiscards());
        this.programmingDeck.shuffle(this.programmingDeck.getCards());
    }

    /**
     * After a player has placed programming cards in each of the five registers, he should
     * place the remaining 4 programming cards in his hand in the discard pile.
     * <p>This method places an arraylist of cards in the player's discard pile.
     */
    public void discard(ArrayList<Card> cards) {
        this.discardPile.getDiscards().addAll(cards);
    }


}

