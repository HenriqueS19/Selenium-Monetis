package pages;

import io.cucumber.java.en.When;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;
import java.util.List;
import org.junit.Assert;

public class PaymentsPage {
    WebDriver driver;
    WebDriverWait wait;

    // selectors
    private By menuPayments = By.xpath("//span[contains(text(),'Payments')]");
    private By menuTransactions = By.xpath("//span[contains(text(),'Transactions')]");
    private By fillEntity = By.name("entity");
    private By fillReference = By.name("reference");
    private By selectAccount = By.id("react-select-3-input");
    private By fillCategory = By.id("react-select-2-input");
    private By fillAmount = By.name("amount");
    private By btnNext = By.xpath("//button[text()='Next']");
    private By btnOtherNext = By.xpath("//button[@type='submit' and text()='Next']");
    private By btnClose = By.xpath("//button[@type='submit' and text()='Close']");

    public PaymentsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void goToPaymentsSection() {
        try {
            wait.until(ExpectedConditions.urlContains("dashboard"));
            Thread.sleep(2000);

            WebElement element = new WebDriverWait(driver, Duration.ofSeconds(20))
                    .until(ExpectedConditions.elementToBeClickable(menuPayments));
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            wait.until(ExpectedConditions.urlContains("payments"));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    public void selectAccount(String account) {
        WebElement accountInput = wait.until(ExpectedConditions.elementToBeClickable(selectAccount));
        accountInput.sendKeys(account);
        accountInput.sendKeys(org.openqa.selenium.Keys.ENTER);
    }

    public void setFillEntity(String entity) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(fillEntity));
        input.sendKeys(entity);
    }

    public void fillReference(String reference) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(fillReference));
        input.sendKeys(reference);
    }

    public void fillCategory(String category) {
        WebElement categoryInput = wait.until(ExpectedConditions.elementToBeClickable(fillCategory));
        categoryInput.sendKeys(category);
        categoryInput.sendKeys(org.openqa.selenium.Keys.ENTER);
    }

    public void setFillAmount(String amount) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(fillAmount));
        input.sendKeys(amount);
    }

    public void verifyConfirmationWindow() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(btnOtherNext));
    }

    public void clickNext() {
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(btnNext));
        nextButton.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public void confirmPayment() {
        WebElement otherNextButton = wait.until(ExpectedConditions.elementToBeClickable(btnOtherNext));
        otherNextButton.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void closeConfirmation() {
        WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(btnClose));
        closeButton.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void verifySuccessPage() {
        wait.until(ExpectedConditions.elementToBeClickable(btnClose));
    }

    public void goToTransactionsSection() {
        By menuTransactions = By.xpath("//span[contains(text(),'Transactions')]");
        try {
            WebElement element = new WebDriverWait(driver, Duration.ofSeconds(20))
                    .until(ExpectedConditions.elementToBeClickable(menuTransactions));
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            wait.until(ExpectedConditions.presenceOfElementLocated(By.className(("transaction-list"))));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void verifyNewTransaction(String category, String amount) {
        By transactionAmount = By.xpath("//div[@class='transaction table']//div[@class='category' and translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')='" + category.toLowerCase() + "']/following::span[@class='amount']");
        String formattedAmount = String.format("%.2f", Double.parseDouble(amount)).replace(".", ",") + " €";
        WebDriverWait waitLong = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement amountElement = waitLong.until(ExpectedConditions.visibilityOfElementLocated(transactionAmount));
        String actualAmount = amountElement.getText();
        Assert.assertEquals("-" + formattedAmount, actualAmount);
    }
}
