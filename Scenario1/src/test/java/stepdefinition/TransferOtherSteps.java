package stepdefinition;

import io.cucumber.java.en.*;
import hooks.Hooks;
import pages.TransferOtherPage;
import pages.LoginPage;
import config.TestConfig;
import utils.ApiUtils;

import static utils.ApiUtils.getIbanByEmail;

public class TransferOtherSteps {

    private LoginPage loginPage;
    private TransferOtherPage transferOtherPage;

    public TransferOtherSteps() {
        this.loginPage = new LoginPage(Hooks.getDriver());
        this.transferOtherPage = new TransferOtherPage(Hooks.getDriver());
    }

    @Given ("login and access transfer page for other account")
    public void login_and_access_transfer_page() {
        loginPage.accessPage(TestConfig.LOGIN_URL);
        loginPage.login(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_USER_PASSWORD);
        transferOtherPage.goToTransferSection();
        transferOtherPage.capturePreviousBalance();
    }

    @When("I select transfer to other account")
    public void i_select_other_account_option() {
        transferOtherPage.selectOtherAccountOption();
    }

    @When("I fill in transfer form with {string} target, {int} amount and proceed")
    public void i_fill_in_transfer_form_with_iban_amount_and_proceed(String target, int amount) {
        String iban = target;
        if (target.contains("@")){
            if(target.equals("temp@email.com")) {
                target = Hooks.getTestEmail();
            }
            iban = getIbanByEmail(target);
        }
        transferOtherPage.enterIBAN(iban);
        transferOtherPage.enterAmount(String.valueOf(amount));
        transferOtherPage.clickNext();
    }

    @Then ("Verify confirmation window appears with transfer details for other account")
    public void verify_confirmation_window_appears_with_transfer_details() {
        transferOtherPage.verifyConfirmationWindow();
    }

    @When("I click to proceed with transfer to other account")
    public void i_click_on_next_button() {
        transferOtherPage.confirmTransfer();
    }

    @Then ("Verify success transfer page appears for other account")
    public void verify_success_transfer_page_appears() {
        transferOtherPage.closeConfirmation();
        transferOtherPage.verifySuccessPage();
    }

    @When("I access accounts page for other account")
    public void i_access_accounts_page() {
        transferOtherPage.goToaccountsSection();
        transferOtherPage.captureAfterBalance();
    }

    @Then("Verify {string} account balance decreased")
    public void verify_account_balance_decreased(String account) {
        transferOtherPage.verifyAccountBalanceDecreased(account);
    }


    @When("I access transactions page")
    public void i_access_transactions_page() {
        transferOtherPage.goToTransactionsSection();
    }

    @Then("Verify new transaction with \"-{int}€\" appears on the list")
    public void verify_new_transaction_with_1000_appears_on_the_list(int amount) {
        transferOtherPage.verifyNewTransactionMinusAmount(amount);
    }

}
