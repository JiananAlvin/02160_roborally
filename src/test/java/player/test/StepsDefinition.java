package player.test;

import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import model.game.element.board.Map;
import model.game.element.Player;

import static org.junit.Assert.*;

public class StepsDefinition {
    Player player;
    Map map;

    @Before
    public void init() {
        this.player = new Player();
    }

    @Given("a player started the game")
    public void aPlayerStartedTheGame() {
        assertTrue(this.player.isPlaying());
    }

    @When("player inputs a name {string}")
    public void playerInputsAName(String arg0) {
        this.player.setName(arg0);
    }

    @Then("this name is assigned to this player")
    public void thisNameIsAssignedToThisPlayer() {
        assertNotNull(this.player.getName());
    }

    @Given("a player has a name {string}")
    public void aPlayerHasAName(String arg0) {
//        this.player.setName(arg0);
        System.out.println(this.player.getName());
        assertNotNull(this.player.getName());

    }

    @When("the player chooses a map {string}")
    public void thePlayerChoosesAMap(String arg0) {
        this.map = new Map(arg0);
    }

    @Then("this map {string} is displayed")
    public void thisMapIsDisplayed(String arg0) {
        assertEquals(arg0, this.map.getContent());
    }
}
