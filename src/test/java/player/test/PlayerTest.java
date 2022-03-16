package player.test;
import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

// specify this specific test is a test that should run with the cucumber feature files
@RunWith(Cucumber.class)
// specify our feature files are actually stored in
@CucumberOptions(features = "src/test/resources/featureFiles", publish=true) 
public class PlayerTest {

}
