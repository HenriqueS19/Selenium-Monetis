package stepdefinition;

import io.cucumber.java.en.*;
import hooks.Hooks;
import pages.AccountsPage;
import pages.LoginPage;
import utils.ApiUtils;
import config.TestConfig;


public class AccountsSteps {

    private LoginPage loginPage;
    private AccountsPage accountsPage;

    public AccountsSteps() {
        this.loginPage = new LoginPage(Hooks.getDriver());
        this.accountsPage = new AccountsPage(Hooks.getDriver());
    }

    @Given("I add money to my account")
    public void i_add_money_to_my_account() {
        ApiUtils.addMoney(TestConfig.INITIAL_MONEY_AMOUNT);

    }

    @Given("login and access accounts page")
    public void login_and_access_accounts_page() {
        loginPage.accessPage(TestConfig.LOGIN_URL);
        loginPage.login(Hooks.getTestEmail(), TestConfig.NEW_USER_PASSWORD);
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
        accountsPage.fillInformation(TestConfig.TEST_ACCOUNT_NAME, TestConfig.TEST_INITIAL_DEPOSIT);
    }

    @And("I click on create account button")
    public void i_click_on_create_account_button() {
        accountsPage.clickCreateAccount();
    }

    @Then("Verify created account is present")
    public void verify_created_account_is_present() {
        accountsPage.verifyAccountCreated(TestConfig.TEST_ACCOUNT_NAME);

    }
}