package runner;

import io.cucumber.junit.CucumberOptions;
import net.serenitybdd.cucumber.CucumberWithSerenity;
import org.junit.runner.RunWith;

@RunWith(CucumberWithSerenity.class)
@CucumberOptions(
        features = {"src/test/resources/features"},
        glue = {"steps"},
        stepNotifications = true,
        plugin = {
                "json:target/cucumber-report/cucumber.json",
                "html:target/cucumber/index.html"
        }
)
public class AllTestsRunner {
}
