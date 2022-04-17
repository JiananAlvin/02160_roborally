package model.game;

import java.util.ArrayList;

import lombok.Data;
import model.game.board.map.element.CheckPoint;
import model.game.board.map.element.Tile;
import model.game.board.mat.element.ProgrammingDeck;
import model.game.board.mat.element.DiscardPile;
import model.game.board.map.element.Robot;
import model.game.board.mat.element.RegisterArea;
import model.game.card.Card;

@Data
public class Player {

    private String name;
    private Robot robot;
    private ProgrammingDeck deck;
    private DiscardPile discard;
    private ArrayList<Card> progCards;
    private ArrayList<Tile> obtainedCheckpointTokens;
    private RegisterArea registerArea;


    public Player(String name, Robot robot) {
        this.name = name;
        this.robot = robot;
        this.deck = new ProgrammingDeck(this);
        this.discard = new DiscardPile(this);
        this.progCards = new ArrayList<>();
        this.obtainedCheckpointTokens = new ArrayList<>();
        this.registerArea = new RegisterArea();
    }

    public Player() {
        this.deck = new ProgrammingDeck(this);
        this.discard = new DiscardPile(this);
        this.progCards = new ArrayList<>();
        this.obtainedCheckpointTokens = new ArrayList<>();
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

    public ArrayList<Card> getProgCards() {
        this.progCards = this.deck.getNRandomCards(9);
        return this.progCards;
    }

    public ArrayList<Card> getDiscard() {
        return this.discard.getDiscard();
    }

    public boolean takeToken(CheckPoint checkPoint) {
        int ownedTokens = this.obtainedCheckpointTokens.size();
        if (this.robot.getPosition().equals(checkPoint.getPosition()) // robot at this checkPoint
                && checkPoint.getCheckPointNum() == ownedTokens + 1 //robot has all the marks before current one
        ) {
            this.obtainedCheckpointTokens.add(checkPoint);
            return true;
        } else return false;
    }
}
