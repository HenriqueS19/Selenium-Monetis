package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;
import org.junit.Assert;

public class PaymentsPage {
    private WebDriver driver;
    private WaitUtils waitUtils;

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
    private By loadingScreen = By.className("loading_screen");



    public PaymentsPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    public void goToPaymentsSection() {
            waitUtils.waitForUrlContains("dashboard");
            waitUtils.waitForElementToDisappear(loadingScreen);
            WebElement element = waitUtils.waitForElementToBeClickableLong(menuPayments);
            Assert.assertTrue("Payments menu is not visible", element.isDisplayed());
            clickElement(element);
            waitUtils.waitForUrlContains("payments");
            Assert.assertTrue("Not on payments page", driver.getCurrentUrl().contains("payments"));
    }

    public void selectAccount(String account) {
        WebElement accountInput = waitUtils.waitForElementToBeClickable(selectAccount);
        accountInput.sendKeys(account);
        accountInput.sendKeys(org.openqa.selenium.Keys.ENTER);
    }

    public void setFillEntity(String entity) {
        WebElement input = waitUtils.waitForElementToBeClickable(fillEntity);
        input.sendKeys(entity);
        Assert.assertEquals("Entity field value does not match", entity, input.getAttribute("value"));
    }

    public void fillReference(String reference) {
        WebElement input = waitUtils.waitForElementToBeClickable(fillReference);
        input.sendKeys(reference);
        Assert.assertEquals("Reference field value does not match", reference, input.getAttribute("value"));
    }

    public void fillCategory(String category) {
        WebElement categoryInput = waitUtils.waitForElementToBeClickable(fillCategory);
        categoryInput.sendKeys(category);
        categoryInput.sendKeys(org.openqa.selenium.Keys.ENTER);
    }

    public void setFillAmount(String amount) {
        WebElement input = waitUtils.waitForElementToBeClickable(fillAmount);
        input.sendKeys(amount);
        Assert.assertEquals("Amount field value does not match", amount, input.getAttribute("value"));
    }

    public void verifyConfirmationWindow() {
        WebElement confirmButton = waitUtils.waitForElementToBeVisible(btnOtherNext);
        Assert.assertTrue("Confirmation window not displayed", confirmButton.isDisplayed());
    }

    public void clickNext() {
        WebElement nextButton = waitUtils.waitForElementToBeClickable(btnNext);
        nextButton.click();
    }
    public void confirmPayment() {
        WebElement otherNextButton = waitUtils.waitForElementToBeClickable(btnOtherNext);
        otherNextButton.click();
    }

    public void closeConfirmation() {
        WebElement closeButton = waitUtils.waitForElementToBeClickable(btnClose);
        closeButton.click();
    }

    public void verifySuccessPage() {
        WebElement closeButton = waitUtils.waitForElementToBeClickable(btnClose);
        Assert.assertTrue("Success page not displayed", closeButton.isDisplayed());
    }

    public void goToTransactionsSection() {
        WebElement element = waitUtils.waitForElementToBeClickableLong(menuTransactions);
        Assert.assertTrue("Transactions menu not visible", element.isDisplayed());
        clickElement(element);
        waitUtils.waitForUrlContains("transactions");
        waitUtils.waitForElementToDisappear(loadingScreen);
    }

    private By getTransactionAmountLocator(String category) {
        return By.xpath("//div[@class='transaction table']//div[@class='category' and translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz')='" + category.toLowerCase() + "']/following::span[@class='amount']");
    }

    public void verifyNewTransaction(String category, String amount) {
        String formattedAmount = String.format("%.2f", Double.parseDouble(amount)).replace(".", ",") + " €";
        WebElement amountElement = waitUtils.waitForElementToBeVisible(getTransactionAmountLocator(category));
        String actualAmount = amountElement.getText().replaceAll("\\s+", " ").trim().toLowerCase();
        String expectedAmount = ("-" + formattedAmount).toLowerCase();
        Assert.assertEquals("Transaction amount does not match expected", expectedAmount, actualAmount);
    }

    private void clickElement(WebElement element) {
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
}
