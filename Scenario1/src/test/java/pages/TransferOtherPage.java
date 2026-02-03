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


public class TransferOtherPage {
    WebDriver driver;
    WebDriverWait wait;

    // selectors
    private By menuTransfer = By.xpath("//*[contains(text(),'Transfer')]");    private By transferBalanceElement = By.xpath("//div[@class='item balance']");
    private By accountsBalanceElement = By.xpath("//div[@class='account']//div[@class='backgroundGradient']//p[contains(text(),'€')]");
    private By optionOtherAccount = By.xpath("//*[contains(text(),'Other Account')]");
    private By inputIBAN = By.name("iban");
    private By amountTransfer = By.name("amount");
    private By btnNext = By.xpath("//button[text()='Next']");
    private By btnNextConfirm = By.xpath("//button[@type='submit' and text()='Next']");
    private By btnClose = By.xpath("//button[@type='submit' and text()='Close']");
    private By btnDetails = By.xpath("//button[contains(text(),'See account details')]");
    private By btnHistory = By.xpath("//div[@class='action' and .//div[text()='History']]");
    private By transactionList = By.className("transaction-list");
    private By checkingBalanceElement = By.xpath("//div[@class='account']//h2[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'checking')]/following-sibling::p[contains(.,'€')]");
    By transactionAmount = By.xpath("//div[@class='transaction table']//div[@class='category' and contains(text(),'To PT50000201231234567890154')]/following::span[@class='amount']");
    private double previousBalance;
    private double afterBalance;

    // Constructor
    public TransferOtherPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void goToTransferSection() {
        try {
            wait.until(ExpectedConditions.urlContains("dashboard"));
            Thread.sleep(2000);

            WebElement element = new WebDriverWait(driver, Duration.ofSeconds(20))
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Transfer')]")));
            Assert.assertTrue("Transfer menu not visible", element.isDisplayed());
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            wait.until(ExpectedConditions.urlContains("transfer"));
            Assert.assertTrue("Not on transfer page", driver.getCurrentUrl().contains("transfer"));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void selectOtherAccountOption() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(optionOtherAccount));
        Assert.assertTrue("Other account option not visible", element.isDisplayed());
        element.click();
    }

    public void enterIBAN(String iban) {
        WebElement ibanInput = wait.until(ExpectedConditions.elementToBeClickable(inputIBAN));
        ibanInput.sendKeys(iban);
        Assert.assertEquals("IBAN not filled correctly", iban, ibanInput.getAttribute("value"));
    }

    public void enterAmount(String amount) {
        WebElement amountInput = wait.until(ExpectedConditions.elementToBeClickable(amountTransfer));
        amountInput.sendKeys(amount);
        Assert.assertEquals("Amount not filled correctly", amount, amountInput.getAttribute("value"));
    }

    public void clickNext() {
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(btnNext));
        Assert.assertTrue("Next button not enabled", nextButton.isEnabled());
        nextButton.click();
    }

    public void verifyConfirmationWindow() {
        WebElement confirmButton = wait.until(ExpectedConditions.visibilityOfElementLocated(btnNextConfirm));
        Assert.assertTrue("Confirmation window not displayed", confirmButton.isDisplayed());
    }

    public void confirmTransfer() {
        WebElement nextConfirmButton = wait.until(ExpectedConditions.elementToBeClickable(btnNextConfirm));
        nextConfirmButton.click();
    }

    public void goToaccountsSection() {
        WebElement seeMoreDetails = wait.until(ExpectedConditions.elementToBeClickable(btnDetails));
        seeMoreDetails.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(checkingBalanceElement));
    }

    public void capturePreviousBalance() {
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.visibilityOfElementLocated(transferBalanceElement));
        String balanceText = element.getText();
        String numberPart = balanceText.replaceAll("[^0-9,]", "").replace(",", ".");
        this.previousBalance = Double.parseDouble(numberPart);
    }

    public void captureAfterBalance() {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(checkingBalanceElement));
        String balanceText = element.getText();
        String numberPart = balanceText.replaceAll("[^0-9,]", "").replace(",", ".");
        this.afterBalance = Double.parseDouble(numberPart);
    }


    public void goToTransactionsSection() {
        WebElement historyButton = wait.until(ExpectedConditions.elementToBeClickable(btnHistory));
        historyButton.click();
    }

    public void verifyAccountBalanceDecreased(String account) {
        Assert.assertTrue("Balance did not decrease as expected: previous=" + previousBalance + " after=" + afterBalance,
                afterBalance < previousBalance);
    }


    public void verifyNewTransactionMinusAmount(int amount) {
        String formattedAmount = "-" + String.format("%.2f", (double) amount).replace(".", ",") + " €";
        WebDriverWait waitLong = new WebDriverWait(driver, Duration.ofSeconds(20));
        WebElement amountElement = waitLong.until(ExpectedConditions.presenceOfElementLocated(transactionAmount));
        String actualAmount = amountElement.getText();
        Assert.assertEquals(formattedAmount, actualAmount);
    }

    public void verifySuccessPage() {
        WebDriverWait waitLong = new WebDriverWait(driver, Duration.ofSeconds(20));
        waitLong.until(ExpectedConditions.presenceOfElementLocated(btnDetails));
    }


    public void closeConfirmation() {
        WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(btnClose));
        Assert.assertTrue("Close button not enabled", closeButton.isEnabled());
        closeButton.click();
    }

}
