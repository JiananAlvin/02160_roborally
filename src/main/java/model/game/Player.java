package model.game;

import java.util.ArrayList;

import model.Game;
import lombok.Data;
import model.game.board.map.Position;
import model.game.board.map.element.CheckPoint;
import model.game.board.map.element.Tile;
import model.game.board.mat.element.ProgrammingDeck;
import model.game.board.mat.element.DiscardPile;
import model.game.board.map.element.Robot;
import model.game.card.Card;

@Data
public class Player {

    private String name;
    private Robot robot;
    private ProgrammingDeck deck;
    private DiscardPile discard;
    private ArrayList<Card> progCards;
    private ArrayList<Tile> achievedCheckPoints;


    public Player() {
        this.deck = new ProgrammingDeck(this);
        this.discard = new DiscardPile(this);
        this.progCards = new ArrayList<>();
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

    public ArrayList<Card> getProgCards() {
        this.progCards = deck.getNRandomCards(9);
        return this.progCards;
    }

    public ArrayList<Card> getDiscard() {
        return this.discard.getDiscard();
    }

    public boolean tryToAddMark(CheckPoint checkPoint) {
        int currentOwnedMark = this.achievedCheckPoints.size();
        if (robot.getPosition().equals(checkPoint.getPosition()) // robot at this checkPoint
                && checkPoint.getCheckPointNum() == currentOwnedMark + 1 //robot has all the marks before current one
        ) {
            this.achievedCheckPoints.add(checkPoint);
            return true;
        } else return false;
    }
}
