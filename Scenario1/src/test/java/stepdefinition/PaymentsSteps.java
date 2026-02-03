package stepdefinition;

import io.cucumber.java.en.*;
import hooks.Hooks;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.PaymentsPage;
import utils.ApiUtils;

import java.time.Duration;

public class PaymentsSteps {
    WebDriver driver = Hooks.getDriver();
    PaymentsPage paymentsPage = new PaymentsPage(driver);

    @Given("login and access payments page")
    public void login_and_access_payments_page() {
        driver.get("https://monetis-delta.vercel.app/login");
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        if (!driver.getCurrentUrl().contains("dashboard")) {
            driver.findElement(By.name("email")).sendKeys("john@email.com");
            driver.findElement(By.name("password")).sendKeys("atec123-");
            driver.findElement(By.xpath("//button[@type='submit']")).click();
            wait.until(ExpectedConditions.urlContains("dashboard"));
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
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
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
