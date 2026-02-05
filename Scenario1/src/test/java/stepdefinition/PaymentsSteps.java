package stepdefinition;

import io.cucumber.java.en.*;
import hooks.Hooks;
import pages.PaymentsPage;
import pages.LoginPage;
import utils.ApiUtils;
import config.TestConfig;

public class PaymentsSteps {
    private LoginPage loginPage;
    private PaymentsPage paymentsPage;

    public PaymentsSteps() {
        this.loginPage = new LoginPage(Hooks.getDriver());
        this.paymentsPage = new PaymentsPage(Hooks.getDriver());
    }

    @Given("login and access payments page")
    public void login_and_access_payments_page() {
        loginPage.accessPage(TestConfig.LOGIN_URL);
        if (!Hooks.getDriver().getCurrentUrl().contains("dashboard")) {
            loginPage.login(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_USER_PASSWORD);
        }
        paymentsPage.goToPaymentsSection();
    }

    @When("I click to proceed with payment")
    public void i_click_to_proceed_with_payment() {
        paymentsPage.clickNext();
    }

    @When("I click to proceed with payment again")
    public void i_click_to_proceed_with_payment_again() {
        paymentsPage.clickNext();
    }

    @Then("Verify success payment page appears")
    public void verify_success_payment_page_appears() {
        paymentsPage.closeConfirmation();
    }

    @When("I make a payment with the following data")
    public void i_make_a_payment_with_the_following_data(io.cucumber.datatable.DataTable dataTable) {
        java.util.List<java.util.Map<String, String>> data = dataTable.asMaps(String.class, String.class);
        java.util.Map<String, String> paymentData = data.get(0);
        String account = paymentData.get("ACCOUNT");

        if (!"checking".equals(account) && !"savings".equals(account)) {
            ApiUtils.createAccount(account, 1);
        }

        paymentsPage.selectAccount(paymentData.get("ACCOUNT"));
        paymentsPage.setFillEntity(paymentData.get("ENTITY"));
        paymentsPage.fillReference(paymentData.get("REFERENCE"));
        paymentsPage.fillCategory(paymentData.get("CATEGORY"));
        paymentsPage.setFillAmount(paymentData.get("AMOUNT"));
    }

    @When("I access the payments transactions page")
    public void i_access_transactions_page() {
        paymentsPage.goToTransactionsSection();
    }

    @Then("Verify new transaction appears with {string} category and {int} amount")
    public void verify_new_transaction_appears_with_category_and_amount(String category, Integer amount) {
        paymentsPage.verifyNewTransaction(category, String.valueOf(amount));
    }

}
