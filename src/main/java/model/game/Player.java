package model.game;


import java.util.ArrayList;

import model.game.board.mat.element.ProgrammingDeck;
import model.game.board.mat.element.DiscardPile;
import model.game.board.map.element.Robot;
import model.game.card.Card;

public class Player {

    //    final static int MAX_PLAYER_NUMBER = 6;
    private String name;
    private boolean isPlaying;
    private boolean hasRobot;
    private Robot robot;
    private boolean progCardsStatus;

    ProgrammingDeck deck = new ProgrammingDeck(this);
    DiscardPile discard = new DiscardPile(this);
    ArrayList<Card> progCards;


    public void setName(String name) {
        this.name = name;
    }

    public boolean isPlaying() {
        return isPlaying;
    }

    public void setPlaying(boolean playing) {
        isPlaying = playing;
    }

    public Player() {
        this.setPlaying(true);
        this.setHasRobot(false);
    }

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
        return this.hasRobot;
    }

    public void setRobot(Robot robot) {
        this.robot = robot;
    }

    public Robot getRobot() {
        return this.robot;
    }

    public void setHasRobot(boolean b) {
        this.hasRobot = b;
    }

    public boolean progCardsStatus() {
        return this.progCardsStatus;
    }

    public ArrayList<Card> getProgCards() {
        this.progCards = deck.getNRandomCards(9);
        return this.progCards;
    }

    public ArrayList<Card> getDiscard() {
        return this.discard.getDiscard();
    }
}
