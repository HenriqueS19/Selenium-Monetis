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
    private By menuTransfer = By.xpath("//[contains(text(),'Transfer')]");
    private By transferBalanceElement = By.xpath("//div[@class='item balance']");
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
    private By checkingBalanceElement = By.xpath("//div[@class='account' and contains(translate(., 'ABCDEFGHIJKLMNOPQRSTUVWXYZ', 'abcdefghijklmnopqrstuvwxyz'), 'checking')]//p[contains(text(),'€')]");
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
                    .until(ExpectedConditions.elementToBeClickable(By.xpath("//span[contains(text(),'Transfer')]")));  // Ajuste o tag se necessário
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void selectOtherAccountOption() {
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(optionOtherAccount));
        element.click();
    }

    public void enterIBAN(String iban) {
        WebElement ibanInput = wait.until(ExpectedConditions.elementToBeClickable(inputIBAN));
        ibanInput.sendKeys(iban);
    }

    public void enterAmount(String amount) {
        WebElement amountInput = wait.until(ExpectedConditions.elementToBeClickable(amountTransfer));
        amountInput.sendKeys(amount);
    }

    public void clickNext() {
        WebElement nextButton = wait.until(ExpectedConditions.elementToBeClickable(btnNext));
        nextButton.click();
    }

    public void verifyConfirmationWindow() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(btnNextConfirm));
    }

    public void confirmTransfer() {
        WebElement nextConfirmButton = wait.until(ExpectedConditions.elementToBeClickable(btnNextConfirm));
        nextConfirmButton.click();
    }

    public void goToaccountsSection() {
        WebElement seeMoreDetails = wait.until(ExpectedConditions.elementToBeClickable(btnDetails));
        seeMoreDetails.click();
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
        WebDriverWait waitLong = new WebDriverWait(driver, Duration.ofSeconds(20));
        waitLong.until(ExpectedConditions.visibilityOfElementLocated(transactionList));
        List<WebElement> transactions = driver.findElements(By.xpath("//div[@class='transaction-list']//div[contains(text(),'-" + amount + "€')]"));
        Assert.assertTrue("Transaction with -" + amount + "€ not found", !transactions.isEmpty());
    }

    public void verifySuccessPage() {
        WebDriverWait waitLong = new WebDriverWait(driver, Duration.ofSeconds(20));
        waitLong.until(ExpectedConditions.presenceOfElementLocated(btnDetails));
    }


    public void closeConfirmation() {
        WebElement closeButton = wait.until(ExpectedConditions.elementToBeClickable(btnClose));
        closeButton.click();
    }

}
