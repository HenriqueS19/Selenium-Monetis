package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;

public class AccountsPage {

    private WebDriver driver;
    private WaitUtils waitUtils;

    // selectors, lets map the page elements
    private By menuAccounts = By.xpath("//*[contains(text(),'Contas') or contains(text(),'Accounts')]");
    private By addNewAccountCard = By.className("new_account");
    private By accountName = By.cssSelector("input[placeholder='My next vacations']");
    private By initialDeposit = By.name("amount");
    private By createButton = By.xpath("//button[text()='Create account']");
    private By loadingScreen = By.className("loading_screen");


    public AccountsPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    public void goToaccountsSection() {
            waitUtils.waitForUrlContains("dashboard");
            Assert.assertTrue("Not on dashboard", driver.getCurrentUrl().contains("dashboard"));

            waitUtils.waitForElementToDisappear(loadingScreen);
            WebElement element = waitUtils.waitForElementToBeClickable(menuAccounts);
            Assert.assertTrue("Menu accounts not visible", element.isDisplayed());
            clickElement(element);

            waitUtils.waitForUrlContains("accounts");
            Assert.assertTrue("Not on accounts page", driver.getCurrentUrl().contains("accounts"));
    }

    public void openCreateAccountPopup() {
        WebElement popup = waitUtils.waitForElementToBeClickable(addNewAccountCard);
        Assert.assertTrue("Create account button not visible", popup.isDisplayed());
        popup.click();
        waitUtils.waitForElementToBeVisible(accountName);
        Assert.assertTrue("Popup did not open", driver.findElement(accountName).isDisplayed());
    }

    public void fillInformation(String nameValue, String initialDepositValue) {
        WebElement nameInput = waitUtils.waitForElementToBeVisible(accountName);
        nameInput.clear();
        nameInput.sendKeys(nameValue);
        Assert.assertEquals("Account name not filled correctly", nameValue, nameInput.getAttribute("value"));

        WebElement depositInput = waitUtils.waitForElementToBeVisible(initialDeposit);
        depositInput.clear();
        depositInput.sendKeys(initialDepositValue);
        Assert.assertEquals("Initial deposit not filled correctly", initialDepositValue, depositInput.getAttribute("value"));
    }

    public void clickCreateAccount() {
        WebElement button = waitUtils.waitForElementToBeClickable(createButton);
        Assert.assertTrue("Create button not enabled", button.isEnabled());
        button.click();
    }

    public void verifyBalanceIncreased(String account) {
        By accountElement = By.xpath("//*[contains(text(),'" + accountName + "')]");
        WebElement element = waitUtils.waitForElementToBeVisible(accountElement);
        Assert.assertTrue("Account '" + accountName + "' was not created.", element.isDisplayed());
    }

    public void verifyAccountCreated(String accountName) {
        By accountElement = By.xpath("//*[contains(text(),'" + accountName + "')]");
        WebElement element = waitUtils.waitForElementToBeVisible(accountElement);
        Assert.assertTrue("Account '" + accountName + "' was not created.", element.isDisplayed());
    }

    private void clickElement(WebElement element) {

        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
}
