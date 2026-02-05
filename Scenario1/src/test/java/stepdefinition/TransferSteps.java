package stepdefinition;

import io.cucumber.java.en.*;
import hooks.Hooks;
import pages.TransferPage;
import pages.LoginPage;
import config.TestConfig;

public class TransferSteps {

    private LoginPage loginPage;
    private TransferPage transferPage;

    public TransferSteps() {
        this.loginPage = new LoginPage(Hooks.getDriver());
        this.transferPage = new TransferPage(Hooks.getDriver());
    }

    @Given("login and access transfer page")
    public void login_and_access_transfer_page() {
        loginPage.accessPage(TestConfig.LOGIN_URL);
        loginPage.login(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_USER_PASSWORD);
        transferPage.goToAccountsSectionFromDashboard();
    }

    @Given("I capture initial balance of {string} account")
    public void i_capture_initial_balance_of_account(String account) {
        transferPage.goToAccountsSectionFromTransfer();
        transferPage.captureBalance(account);
        transferPage.goToTransferSection();
    }

    @When("I select transfer to own account")
    public void i_select_own_account_option() {
        transferPage.selectOwnAccountOption();
    }

    @When("I fill in transfer form with {string} account, {int} amount and proceed")
    public void i_fill_transfer_amount(String account, int amount) {
        transferPage.selectSavingsAccount(account);
        transferPage.fillTransferAmount(String.valueOf(amount));
        transferPage.clickNextButton();
    }

    @Then("Verify confirmation window appears with transfer details")
    public void verify_confirmation_window_appears_with_transfer_details() {
        transferPage.verifyConfirmationWindow();
    }

    @When("I click to proceed with transfer")
    public void i_click_on_next_button() {
        transferPage.clickNextConfirmButton();
    }

    @Then("Verify success transfer page appears")
    public void verify_success_transfer_page_appears() {
        transferPage.verifySuccessAndClose();
    }

    @When("I access accounts page")
    public void i_access_accounts_page() {
        transferPage.goToAccountsSectionFromTransfer();
    }

    @Then("verify {string} account balance increased")
    public void verify_account_balance_increased(String account) {
        transferPage.captureFinalBalance(account);
        transferPage.verifyBalanceIncreased(account);
    }
}