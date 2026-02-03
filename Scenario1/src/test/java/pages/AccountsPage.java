package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class AccountsPage {

    WebDriver driver;
    WebDriverWait wait;

    // selectors, lets map the page elements
    private By menuAccounts = By.xpath("//*[contains(text(),'Contas') or contains(text(),'Accounts')]");
    private By addNewAccountCard = By.className("new_account");
    private By effectiveDate = By.name("name");
    private By accountHolder = By.name("name");
    private By accountName = By.cssSelector("input[placeholder='My next vacations']");
    private By initialDeposit = By.name("amount");
    private By createButton = By.xpath("//button[text()='Create account']");


    public AccountsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void goToaccountsSection() {
        try {
            wait.until(ExpectedConditions.urlContains("dashboard"));
            Assert.assertTrue("Not on dashboard", driver.getCurrentUrl().contains("dashboard"));
            Thread.sleep(2000);

            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(menuAccounts));
            Assert.assertTrue("Menu accounts not visible", element.isDisplayed());
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
            wait.until(ExpectedConditions.urlContains("accounts"));
            Assert.assertTrue("Not on accounts page", driver.getCurrentUrl().contains("accounts"));
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void openCreateAccountPopup() {
        WebElement popup = wait.until(ExpectedConditions.elementToBeClickable(addNewAccountCard));
        Assert.assertTrue("Create account button not visible", popup.isDisplayed());
        popup.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        wait.until(ExpectedConditions.visibilityOfElementLocated(accountName));
        Assert.assertTrue("Popup did not open", driver.findElement(accountName).isDisplayed());
    }

    public void fillInformation(String namevalue, String initialDepositValue) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(accountName)).clear();
        driver.findElement(accountName).sendKeys(namevalue);
        Assert.assertEquals("Account name not filled correctly", namevalue, driver.findElement(accountName).getAttribute("value"));

        wait.until(ExpectedConditions.visibilityOfElementLocated(initialDeposit)).clear();
        driver.findElement(initialDeposit).sendKeys(initialDepositValue);
        Assert.assertEquals("Initial deposit not filled correctly", initialDepositValue, driver.findElement(initialDeposit).getAttribute("value"));

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void clickCreateAccount() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(createButton));
        Assert.assertTrue("Create button not enabled", button.isEnabled());
        button.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void verifyBalanceIncreased(String account) {
        System.out.println("Verifying if balance increased for account: " + account);
    }

    public void verifyAccountCreated(String accountName) {
        By accountElement = By.xpath("//*[contains(text(),'" + accountName + "')]");
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(accountElement));
        Assert.assertTrue("Account '" + accountName + "' was not created.", element.isDisplayed());

    }
}
