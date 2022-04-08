package client.test;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.game.Player;
import model.game.card.Card;

import java.util.ArrayList;

import static org.junit.Assert.assertEquals;

public class GameProcessStepsDefinition {
    private Player player;
    private ArrayList<Card> register;

    @Before
    public void init() {
        this.player = new Player();
    }


    @Given("a player {string} is in programming phase")
    public void aPlayerIsInProgrammingPhase(String arg0) {
        this.player.setName(arg0);
    }

    @When("he gets programming cards from card desk")
    public void heGetsProgrammingCardsFromCardDesk() {
        this.register = this.player.getProgCards();
    }

    @Then("Player gets {int} cards")
    public void player_gets_cards(Integer int1) {
        assertEquals((int) int1, this.register.size());
    }

}
