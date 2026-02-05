package stepdefinition;

import io.cucumber.java.en.*;
import hooks.Hooks;
import pages.LoginPage;
import org.junit.Assert;

public class LoginSteps {
    // we initialize the LoginPage by passing the driver created in the Hooks class
    private LoginPage loginPage = new LoginPage(Hooks.getDriver());

    @Given("access the login page {string}")
    public void access_the_login_page(String url) {
        // calls the page object method to navigate to the URL
        loginPage.accessPage(url);
    }

    @When("I Fill in username from hook and password {string}")
    public void i_fill_username_from_hook_and_password(String pass) {
        loginPage.fillCredentials(Hooks.getTestEmail(),pass);
    }

    @When("I click on the login button")
    public void i_click_on_the_login_button() {
        loginPage.clickLogin();
    }

    @Then("Verify user is on dashboard")
    public void verify_user_is_on_dashboard() {
        boolean isDashboard = loginPage.isOnDashboard();
        Assert.assertTrue("The User is not on the dashboard page! Current URL: " + Hooks.getDriver().getCurrentUrl(), isDashboard);
    }
}
