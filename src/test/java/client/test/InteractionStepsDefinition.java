package client.test;

import content.RobotName;
import io.cucumber.java.Before;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import lombok.SneakyThrows;
import model.game.Player;
import model.game.board.map.element.Robot;
import model.game.board.mat.element.ProgrammingDeck;
import model.game.board.mat.element.RegisterArea;
import model.game.card.Card;

import java.util.ArrayList;
import java.util.Random;

import static org.junit.Assert.*;

public class InteractionStepsDefinition {
    private Player p1;
    private ArrayList<String> namesOfCardsInHand;
    private ArrayList<String> namesOfCardsInRegisters;
    private ArrayList<String> namesOfRemainingCards;

    @Before
    public void init() {
        this.p1 = new Player("group10", new Robot(RobotName.HULK_X90));
    }

    @Given("a player had {string} cards in his programming deck")
    public void aPlayerHadCardsInHisProgrammingDeck(String arg0) {
        ArrayList<Card> discards = new ArrayList<>(this.p1.getProgrammingDeck().getCards().subList(0, this.p1.getProgrammingDeck().getCards().size() - Integer.parseInt(arg0)));
        this.p1.getProgrammingDeck().getCards().subList(0, this.p1.getProgrammingDeck().getCards().size() - Integer.parseInt(arg0)).clear();
        this.p1.getDiscardPile().getDiscards().addAll(discards);
    }

    @When("the player draws nine cards from his programming deck")
    public void the_player_draws_nine_cards_from_his_programming_deck() {
        ArrayList<Card> cardsInHand = this.p1.drawCards();
    }

    @Then("the player now has {string} cards in his programming deck")
    public void the_player_now_has_cards_in_his_programming_deck(String string) {
        assertEquals(Integer.parseInt(string), this.p1.getProgrammingDeck().getCards().size());
    }

    @Then("he now has {string} cards in his discard pile")
    public void he_now_has_cards_in_his_discard_pile(String string) {
        assertEquals(Integer.parseInt(string), this.p1.getDiscardPile().getDiscards().size());
    }

    @Then("he now has {string} cards in his hand")
    public void he_now_has_cards_in_his_hand(String string) {
        assertEquals(Integer.parseInt(string), this.p1.drawCards().size());
    }


    //--------------------------------------------------------------------------------------------
    @Given("a player had nine cards in his hands")
    public void aPlayerHadNineCardsInHisHands() {
        ArrayList<Card> cardsInHand = new ArrayList<>(this.p1.getProgrammingDeck().getCards().subList(0, ProgrammingDeck.NUMBER_OF_CARDS_DRAWN_IN_EACH_ROUND));
        this.namesOfCardsInHand = new ArrayList<>();
        cardsInHand.forEach((card) -> this.namesOfCardsInHand.add(card.getClass().getSimpleName()));
        this.namesOfCardsInRegisters = new ArrayList<>() {
            {
                for (int i = 0; i < RegisterArea.REGISTER_QUEUE_SIZE; i++)
                    add("null");
            }
        };
    }

    @And("the player has finished {string} registers.")
    public void thePlayerHasFinishedRegisters(String arg0) {
        Random rand = new Random();
        for (int i = 0; i < Integer.parseInt(arg0); i++) {
            while (true) {
                int random_index = rand.nextInt(RegisterArea.REGISTER_QUEUE_SIZE);
                if (this.namesOfCardsInRegisters.get(random_index).equals("null")) {
                    this.namesOfCardsInRegisters.set(random_index, this.namesOfCardsInHand.get(i));
                    break;
                }
            }
        }
        this.namesOfRemainingCards = new ArrayList<>(namesOfCardsInHand.subList(Integer.parseInt(arg0), namesOfCardsInHand.size() - 1));
    }

    @When("the time runs out but the player did not finish programming but only {string} registers")
    public void theTimeRunsOutButThePlayerDidNotFinishProgrammingButOnlyRegisters(String arg0) {
        assertTrue(Integer.parseInt(arg0) < RegisterArea.REGISTER_QUEUE_SIZE);
    }

    @SneakyThrows
    @Then("his unfinished registers will be filled randomly with the remaining programming cards in his hand.")
    public void hisUnfinishedRegistersWillBeFilledRandomlyWithTheRemainingProgrammingCardsInHisHand() {
        this.p1.getRegisterArea().addRandomCard(this.namesOfCardsInRegisters, this.namesOfRemainingCards);
    }

    @And("he now has {string} cards in each of registers")
    public void heNowHasCardsInEachOfRegisters(String arg0) {
        assertTrue(Integer.parseInt(arg0) == this.p1.getRegisterArea().getRegisters().size());
        for (Card register: this.p1.getRegisterArea().getRegisters()) {
            assertNotEquals("null", register.getClass().getSimpleName());
        }
    }

//    @And("the programming result will be put to sever")
//    public void theProgrammingResultWillBePutToSever() {
//
//    }



}
