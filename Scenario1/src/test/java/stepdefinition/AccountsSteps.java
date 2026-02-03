package stepdefinition;

import io.cucumber.java.en.*;
import hooks.Hooks;
import io.restassured.RestAssured;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.AccountsPage;
import pages.LoginPage;
import org.junit.Assert;
import utils.ApiUtils;

import java.time.Duration;

public class AccountsSteps {

    WebDriver driver = Hooks.getDriver();
    AccountsPage accountsPage = new AccountsPage(driver);

    @Given("I add money to my account")
    public void i_add_money_to_my_account() {
        ApiUtils.addMoney(300);
    }

    @Given("login and access accounts page")
    public void login_and_access_accounts_page() {
        driver.get("https://monetis-delta.vercel.app/login");

        driver.findElement(By.name("email")).sendKeys(Hooks.getTestEmail());
        driver.findElement(By.name("password")).sendKeys("thisIsMyPassword!1");
        driver.findElement(By.xpath("//button[@type='submit']")).click();

        accountsPage.goToaccountsSection();
    }

    @When("I click on create new account card")
    public void i_click_on_create_new_account_card() {
        accountsPage.openCreateAccountPopup();
    }

    @Then("Verify popup to create the account appears")
    public void verify_popup_to_create_the_account_appears() {
    }

    @When("I fill information for the new account")
    public void i_fill_information_for_the_new_account() {
        accountsPage.fillInformation("Ibiza", "200");
    }

    @And("I click on create account button")
    public void i_click_on_create_account_button() {
        accountsPage.clickCreateAccount();
    }

    @Then("Verify created account is present")
    public void verify_created_account_is_present() {
        accountsPage.verifyAccountCreated("Ibiza");
    }
}