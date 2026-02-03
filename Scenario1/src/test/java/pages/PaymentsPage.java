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
    private By transactionList = By.className("transaction-list");


    public PaymentsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void goToPaymentsSection() {
            wait.until(ExpectedConditions.urlContains("dashboard"));
            WebElement element = new WebDriverWait(driver, Duration.ofSeconds(20))
                    .until(ExpectedConditions.elementToBeClickable(menuPayments));
            Assert.assertTrue("Payments menu is not visible", element.isDisplayed());
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            wait.until(ExpectedConditions.urlContains("payments"));
            Assert.assertTrue("Not on payments page", driver.getCurrentUrl().contains("payments"));
         try {
            Thread.sleep(2000);
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
        Assert.assertEquals("Entity field value does not match", entity, input.getAttribute("value"));
    }

    public void fillReference(String reference) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(fillReference));
        input.sendKeys(reference);
        Assert.assertEquals("Reference field value does not match", reference, input.getAttribute("value"));
    }

    public void fillCategory(String category) {
        WebElement categoryInput = wait.until(ExpectedConditions.elementToBeClickable(fillCategory));
        categoryInput.sendKeys(category);
        categoryInput.sendKeys(org.openqa.selenium.Keys.ENTER);
    }

    public void setFillAmount(String amount) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(fillAmount));
        input.sendKeys(amount);
        Assert.assertEquals("Amount field value does not match", amount, input.getAttribute("value"));
    }

    public void verifyConfirmationWindow() {
        WebElement confirmButton = wait.until(ExpectedConditions.visibilityOfElementLocated(btnOtherNext));
        Assert.assertTrue("Confirmation window not displayed", confirmButton.isDisplayed());
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
        WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(btnClose));
        Assert.assertTrue("Success page not displayed", closeButton.isDisplayed());
    }

    public void goToTransactionsSection() {
        try {
            WebElement element = new WebDriverWait(driver, Duration.ofSeconds(20))
                    .until(ExpectedConditions.elementToBeClickable(menuTransactions));
            Assert.assertTrue("Transactions menu not visible", element.isDisplayed());
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            wait.until(ExpectedConditions.presenceOfElementLocated(transactionList));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private By getTransactionAmountLocator(String category) {
        return By.xpath("//div[@class='transaction table']//div[@class='category' and translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')='" + category.toLowerCase() + "']/following::span[@class='amount']");
    }

    public void verifyNewTransaction(String category, String amount) {
        String formattedAmount = String.format("%.2f", Double.parseDouble(amount)).replace(".", ",") + " €";
        WebDriverWait waitLong = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement amountElement = waitLong.until(ExpectedConditions.visibilityOfElementLocated(getTransactionAmountLocator(category)));
        String actualAmount = amountElement.getText().replaceAll("\\s+", " ").trim().toLowerCase();
        String expectedAmount = ("-" + formattedAmount).toLowerCase();
        Assert.assertEquals("Transaction amount does not match expected", expectedAmount, actualAmount);
    }
}
