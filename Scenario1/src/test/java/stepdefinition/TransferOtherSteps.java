package stepdefinition;

import io.cucumber.java.en.*;
import hooks.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.TransferOtherPage;
import pages.TransferPage;
import java.time.Duration;

public class TransferOtherSteps {
    WebDriver driver = Hooks.getDriver();
    TransferOtherPage transferOtherPage = new TransferOtherPage(driver);

    @Given ("login and access transfer page for other account")
    public void login_and_access_transfer_page() {
        driver.get("https://monetis-delta.vercel.app/login");
        driver.findElement(By.name("email")).sendKeys("john@email.com");
        driver.findElement(By.name("password")).sendKeys("atec123-");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("dashboard"));
        transferOtherPage.goToTransferSection();
        transferOtherPage.capturePreviousBalance();
    }

    @When("I select transfer to other account")
    public void i_select_other_account_option() {
        transferOtherPage.selectOtherAccountOption();
    }

    @When("I fill in transfer form with {string} IBAN, {int} amount and proceed")
    public void i_fill_in_transfer_form_with_iban_amount_and_proceed(String iban, int amount) {
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
