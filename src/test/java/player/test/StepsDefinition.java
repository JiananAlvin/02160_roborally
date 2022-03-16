package player.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertNull;

import GlobalInformationManager.InfoManager;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import roborally.player.Player;
import roborally.player.Room;

public class StepsDefinition {
	String name;
	InfoManager IM;
	Player p;
	Room r;
	@Before
	public void initialize() {
		IM = new InfoManager();
	}
	@Given("I input my name {string}")
	public void i_input_my_name(String string) {
		p = new Player(string);
		IM.storePlayer(p);
	}
	@Then("I validate the name stored in InfoManager")
	public void i_validate_the_name_stored_in_info_manager() {
		assertEquals(IM.getPlayer(), this.p);
	}

	@Given("that I do not want to join a room")
	public void that_i_do_not_want_to_join_a_room() {
		
	}
	@Then("I create a room")
	public void i_create_a_room() {
		r = new Room();
		IM.setRoom(r);
	}
	
	@Given("that I am not in a room")
	public void that_i_am_not_in_a_room() {
		assertNull(IM.getRoom());
	}

	@Then("I join a room with room number {string}")
	public void i_join_a_room_with_room_number(String string) {
		IM.setRoom(string);
		assertNotNull(IM.getRoom());
	}


}
