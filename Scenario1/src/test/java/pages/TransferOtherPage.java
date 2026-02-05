package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;
import org.junit.Assert;


public class TransferOtherPage {
    private WebDriver driver;
    private WaitUtils waitUtils;

    // selectors
    private By menuTransfer = By.xpath("//*[contains(text(),'Transfer')]");
    private By transferBalanceElement = By.xpath("//div[@class='item balance']");
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
    private By transactionAmount = By.xpath("//div[@class='transaction table']//div[@class='category' and contains(text(),'To PT50000201231234567890154')]/following::span[@class='amount']");

    private double previousBalance;
    private double afterBalance;

    // Constructor
    public TransferOtherPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    public void goToTransferSection() {
        waitUtils.waitForUrlContains("dashboard");

        WebElement element = waitUtils.waitForElementToBeClickableLong(By.xpath("//span[contains(text(),'Transfer')]"));
        Assert.assertTrue("Transfer menu not visible", element.isDisplayed());
        clickElement(element);
        waitUtils.waitForUrlContains("transfer");
        Assert.assertTrue("Not on transfer page", driver.getCurrentUrl().contains("transfer"));
    }

    public void selectOtherAccountOption() {
        WebElement element = waitUtils.waitForElementToBeClickable(optionOtherAccount);
        Assert.assertTrue("Other account option not visible", element.isDisplayed());
        element.click();
    }

    public void enterIBAN(String iban) {
        WebElement ibanInput = waitUtils.waitForElementToBeClickable(inputIBAN);
        ibanInput.sendKeys(iban);
        Assert.assertEquals("IBAN not filled correctly", iban, ibanInput.getAttribute("value"));
    }

    public void enterAmount(String amount) {
        WebElement amountInput = waitUtils.waitForElementToBeClickable(amountTransfer);
        amountInput.sendKeys(amount);
        Assert.assertEquals("Amount not filled correctly", amount, amountInput.getAttribute("value"));
    }

    public void clickNext() {
        WebElement nextButton = waitUtils.waitForElementToBeClickable(btnNext);
        Assert.assertTrue("Next button not enabled", nextButton.isEnabled());
        nextButton.click();
    }

    public void verifyConfirmationWindow() {
        WebElement confirmButton = waitUtils.waitForElementToBeClickable(btnNextConfirm);
        Assert.assertTrue("Confirmation window not displayed", confirmButton.isDisplayed());
    }

    public void confirmTransfer() {
        WebElement nextConfirmButton = waitUtils.waitForElementToBeClickable(btnNextConfirm);
        nextConfirmButton.click();
    }

    public void goToaccountsSection() {
        WebElement seeMoreDetails = waitUtils.waitForElementToBeClickable(btnDetails);
        seeMoreDetails.click();
        waitUtils.waitForElementPresence(checkingBalanceElement);
    }

    public void capturePreviousBalance() {
        WebElement element = waitUtils.waitForElementToBeVisible(transferBalanceElement);
        String balanceText = element.getText();
        String numberPart = balanceText.replaceAll("[^0-9,]", "").replace(",", ".");
        this.previousBalance = Double.parseDouble(numberPart);
    }

    public void captureAfterBalance() {
        WebElement element = waitUtils.waitForElementToBeVisible(checkingBalanceElement);
        String balanceText = element.getText();
        String numberPart = balanceText.replaceAll("[^0-9,]", "").replace(",", ".");
        this.afterBalance = Double.parseDouble(numberPart);
    }

    public void goToTransactionsSection() {
        WebElement historyButton = waitUtils.waitForElementToBeClickable(btnHistory);
        historyButton.click();
    }

    public void verifyAccountBalanceDecreased(String account) {
        Assert.assertTrue("Balance did not decrease as expected: previous=" + previousBalance + " after=" + afterBalance,
                afterBalance < previousBalance);
    }


    public void verifyNewTransactionMinusAmount(int amount) {
        String formattedAmount = "-" + String.format("%.2f", (double) amount).replace(".", ",") + " €";
        WebElement amountElement = waitUtils.waitForElementToBeVisible(transactionAmount);
        String actualAmount = amountElement.getText();
        Assert.assertEquals(formattedAmount, actualAmount);
    }

    public void verifySuccessPage() {
        waitUtils.waitForElementPresence(btnDetails);
    }


    public void closeConfirmation() {
        WebElement closeButton = waitUtils.waitForElementToBeClickable(btnClose);
        Assert.assertTrue("Close button not enabled", closeButton.isEnabled());
        closeButton.click();
    }

    private void clickElement(WebElement element) {
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
}
