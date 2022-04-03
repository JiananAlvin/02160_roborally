package model.game;

import java.util.ArrayList;

import model.game.board.mat.element.ProgrammingDeck;
import model.game.board.mat.element.DiscardPile;
import model.game.board.map.element.Robot;
import model.game.card.Card;

public class Player {

    private String name;
//    private boolean isPlaying;
    private boolean hasRobot;
    private Robot robot;
//    private boolean progCardsStatus;
    private ProgrammingDeck deck;
    private DiscardPile discard;
    private ArrayList<Card> progCards;

    public Player() {
        // TODO:
        // Check the meaning of @method: setPlaying(true)
//        this.setPlaying(true);
//        this.setHasRobot(false);
        this.deck = new ProgrammingDeck(this);
        this.discard = new DiscardPile(this);
        this.progCards = new ArrayList<>();
    }

    public void setName(String name) {
        this.name = name;
    }

//    public boolean isPlaying() {
//        return isPlaying;
//    }

//    public void setPlaying(boolean playing) {
//        isPlaying = playing;
//    }
//
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
        return this.robot != null;
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
    }

    public Robot getRobot() {
        return this.robot;
    }

//    public void setHasRobot(boolean b) {
//        this.hasRobot = b;
//    }

//    public boolean progCardsStatus() {
//        return this.progCardsStatus;
//    }

    public ArrayList<Card> getProgCards() {
        this.progCards = deck.getNRandomCards(9);
        return this.progCards;
    }

    public ArrayList<Card> getDiscard() {
        return this.discard.getDiscard();
    }
}
