package pages;

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

    // Constructor, receives the test driver to use on this page
    public AccountsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void goToaccountsSection() {
        try {
            wait.until(ExpectedConditions.urlContains("dashboard"));
            Thread.sleep(2000);

            WebElement element = wait.until(ExpectedConditions.elementToBeClickable(menuAccounts));
            ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void openCreateAccountPopup() {
        wait.until(ExpectedConditions.elementToBeClickable(addNewAccountCard)).click();
    }

    public void fillInformation(String namevalue, String initialDepositValue) {
        // wait account name filled and visible
        wait.until(ExpectedConditions.visibilityOfElementLocated(accountName)).clear();
        driver.findElement(accountName).sendKeys(namevalue);

        // deposit
        wait.until(ExpectedConditions.visibilityOfElementLocated(initialDeposit)).clear();
        driver.findElement(initialDeposit).sendKeys(initialDepositValue);
    }

    public void clickCreateAccount() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(createButton));
        button.click();
    }

    public void verifyBalanceIncreased(String account) {

        System.out.println("A verificar se o saldo da conta " + account + " aumentou.");
    }
}
