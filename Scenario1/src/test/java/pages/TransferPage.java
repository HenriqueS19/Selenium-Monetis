package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;
import config.TestConfig;

public class TransferPage {

    private WebDriver driver;
    private WaitUtils waitUtils;


    private By menuTransfer = By.xpath("//*[contains(text(),'Transfer')]");
    private By menuAccounts = By.xpath("//*[contains(text(),'Accounts')]");
    private By optionOwnAccount = By.xpath("//*[contains(text(),'Own Account')]");
    private By selectAccount = By.xpath("(//input[contains(@id,'react-select') and contains(@id,'-input')])[2]");
    private By amountTransfer = By.name("amount");
    private By btnNext = By.xpath("//button[text()='Next']");
    private By btnNextConfirm = By.xpath("//button[@type='submit' and text()='Next']");
    private By btnClose = By.xpath("//button[@type='submit' and text()='Close']");
    private By loadingScreen = By.className("loading_screen");

    private double previousBalance;
    private double finalBalance;

    private By initialBalance(String accountName) {
        return By.xpath("//div[@class='account']//h2[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'" + accountName.toLowerCase() + "')]/following-sibling::p[contains(.,'€')]");
    }

    private By lastBalance(String accountName) {
        return By.xpath("//div[@class='account']//h2[contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'" + accountName.toLowerCase() + "')]/following-sibling::p[contains(.,'€')]");
    }


    public TransferPage(WebDriver driver) {
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    public void goToTransferSection() {
        String currentUrl = driver.getCurrentUrl();
        if (!currentUrl.contains("dashboard") && !currentUrl.contains("accounts")) {
            driver.get(TestConfig.DASHBOARD_URL);
        }
        WebElement element = waitUtils.waitForElementToBeClickableLong(menuTransfer);
        Assert.assertTrue(element.isDisplayed());
        clickElement(element);
        waitUtils.waitForUrlContains("transfer");
        Assert.assertTrue(driver.getCurrentUrl().contains("transfer"));
    }

    public void goToAccountsSectionFromDashboard() {
        String currentUrl = driver.getCurrentUrl();
        if (!currentUrl.contains("dashboard") && !currentUrl.contains("accounts")) {
            driver.get(TestConfig.DASHBOARD_URL);
        }
        WebElement element = waitUtils.waitForElementToBeClickableLong(menuAccounts);
        Assert.assertTrue("Accounts menu is not visible", element.isDisplayed());
        clickElement(element);
        waitUtils.waitForUrlContains("accounts");
        Assert.assertTrue("Not on accounts page", driver.getCurrentUrl().contains("accounts"));
    }

    public void goToAccountsSectionFromTransfer() {
        String currentUrl = driver.getCurrentUrl();

        if (currentUrl.contains("accounts")) {
            waitUtils.waitForElementToBeInvisible(loadingScreen);
            return;
        }

        if (!currentUrl.contains("dashboard")) {
            driver.get(TestConfig.DASHBOARD_URL);
            waitUtils.waitForUrlContains("dashboard");
        }

        waitUtils.waitForElementToBeInvisible(loadingScreen);

        WebElement element = waitUtils.waitForElementToBeClickableLong(menuAccounts);
        Assert.assertTrue(element.isEnabled());
        clickElement(element);
        waitUtils.waitForUrlContains("accounts");
        Assert.assertTrue(driver.getCurrentUrl().contains("accounts"));
    }

    public void selectOwnAccountOption() {
        waitUtils.waitForElementToDisappear(loadingScreen);
        WebElement option = waitUtils.waitForElementToBeClickable(optionOwnAccount);
        Assert.assertTrue("Own account option is not displayed", option.isDisplayed());
        option.click();
        waitUtils.waitForElementPresence(selectAccount);
    }

    public void selectSavingsAccount(String accountName) {
        waitUtils.waitForElementPresence(selectAccount);
        WebElement selectElem = waitUtils.waitForElementToBeClickable(selectAccount);
        selectElem.click();

        waitUtils.waitForElementToBeVisible(By.xpath("//div[@role='listbox']"));

        String xpathOption = "//div[@role='option' and contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'" + accountName.toLowerCase() + "')]";
        WebElement option = waitUtils.waitForElementToBeClickable(By.xpath(xpathOption));
        option.click();
    }


    public void fillTransferAmount(String amount) {
        WebElement amoutField = waitUtils.waitForElementToBeVisible(amountTransfer);
        amoutField.clear();
        amoutField.sendKeys(amount);
    }

    public void clickNextButton() {
        WebElement button = waitUtils.waitForElementToBeClickable(btnNext);
        button.click();
    }

    public void clickNextConfirmButton() {
        WebElement button = waitUtils.waitForElementToBeClickable(btnNextConfirm);
        button.click();
    }
    public void clickCloseButton() {
        WebElement button = waitUtils.waitForElementToBeClickable(btnClose);
        button.click();
    }

    public void verifyConfirmationWindow() {
        waitUtils.waitForElementToBeVisible(btnNextConfirm);
    }

    public void captureBalance(String accountName) {
        waitUtils.waitForElementToBeInvisible(loadingScreen);
        WebElement element = waitUtils.waitForElementToBeVisible(lastBalance(accountName));
        String balanceText = element.getText().replaceAll("[^0-9.,]", "").replace(".", "").replace(",", ".");
        previousBalance = Double.parseDouble(balanceText);
    }

    public void captureFinalBalance(String accountName) {
        WebElement element = waitUtils.waitForElementToBeVisible(lastBalance(accountName));
        String balanceText = element.getText().replaceAll("[^0-9.,]", "").replace(".", "").replace(",", ".");
        finalBalance = Double.parseDouble(balanceText);
    }

    public void verifySuccessAndClose() {
        clickCloseButton();
    }

    public void verifyBalanceIncreased(String accountName) {
        Assert.assertTrue("Balance did not increase: previous=" + previousBalance + " final=" + finalBalance, finalBalance > previousBalance);
    }

    private void clickElement(WebElement element) {
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
    }
}
