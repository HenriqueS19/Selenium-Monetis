package runner;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        features = "src/test/resources/features", // Path to your .feature files
        glue = {"stepdefinition", "hooks"},      // Packages where your code is
        plugin = {"pretty", "html:target/cucumber-reports.html"}, // Generates a nice report
        monochrome = true                        // Makes the console output readable
)
public class RunTest {
    // This class remains empty
}