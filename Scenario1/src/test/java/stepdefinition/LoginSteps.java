package stepdefinition;

import io.cucumber.java.en.*;
import hooks.Hooks;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.LoginPage;
import org.junit.Assert;
import java.time.Duration;

public class LoginSteps {
    // we initialize the LoginPage by passing the driver created in the Hooks class
    private LoginPage loginPage = new LoginPage(Hooks.getDriver());

    @Given("access the login page {string}")
    public void access_the_login_page(String url) {
        // calls the page object method to navigate to the URL
        loginPage.accessPage(url);
    }

    @When("I Fill in username {string} and password {string}")
    public void i_fill_in_username_and_password(String user, String pass) {
        loginPage.fillCredentials(user,pass);
    }

    @When("I click on the login button")
    public void i_click_on_the_login_button() {
        loginPage.clickLogin();
    }

    @Then("Verify user is on dashboard")
    public void verify_user_is_on_dashboard() {
        // Initialize explicit wait for up to 10 seconds
        WebDriverWait wait = new WebDriverWait(Hooks.getDriver(), Duration.ofSeconds(10));
        boolean isDashboard = wait.until(ExpectedConditions.urlContains("dashboard"));

        // Final validation: confirms the redirection was successful
        Assert.assertTrue("The User is not on the dashboard page! Current URL: " + Hooks.getDriver().getCurrentUrl(), isDashboard);
    }
}
