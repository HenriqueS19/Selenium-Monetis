package stepdefinition;

import io.cucumber.java.en.*;
import hooks.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.AccountsPage;
import pages.LoginPage;
import org.junit.Assert;
import pages.TransferPage;
import java.time.Duration;

public class TransferSteps {
    WebDriver driver = Hooks.getDriver();
    TransferPage transferPage = new TransferPage(driver);
    AccountsPage accountsPage = new AccountsPage(driver);

    @Given("login and access transfer page")
    public void login_and_access_transfer_page() {
        driver.get("https://monetis-delta.vercel.app/login");
        driver.findElement(By.name("email")).sendKeys("john@email.com");
        driver.findElement(By.name("password")).sendKeys("atec123-");
        driver.findElement(By.xpath("//button[@type='submit']")).click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        wait.until(ExpectedConditions.urlContains("dashboard"));
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
    public void i_fill_transfer_amount(String account,int amount) {
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
    public void i_access_accounts_page(){
        transferPage.goToAccountsSectionFromTransfer();
    }

    @Then("verify {string} account balance increased")
    public void verify_account_balance_increased(String account) {
        transferPage.captureFinalBalance(account);
        transferPage.verifyBalanceIncreased(account);
    }
}
