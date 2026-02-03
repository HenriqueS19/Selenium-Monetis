package pages;

import io.cucumber.java.en.When;
import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class TransferPage {

    WebDriver driver;
    WebDriverWait wait;


    private By menuTransfer = By.xpath("//*[contains(text(),'Transfer')]");
    private By menuAccounts = By.xpath("//*[contains(text(),'Accounts')]");
    private By optionOwnAccount = By.xpath("//*[contains(text(),'Own Account')]");
    private By selectAccount = By.xpath("(//input[contains(@id,'react-select') and contains(@id,'-input')])[2]");
    private By amountTransfer = By.name("amount");
    private By btnNext = By.xpath("//button[text()='Next']");
    private By btnNextConfirm = By.xpath("//button[@type='submit' and text()='Next']");
    private By btnClose = By.xpath("//button[@type='submit' and text()='Close']");
    private By confirmationWindow = By.xpath("//div[contains(@class,'confirmation')]");
    private By successPage = By.xpath("//div[contains(text(),'Transfer successful')]");
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
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void goToTransferSection() {
        String currentUrl = driver.getCurrentUrl();
        if (!currentUrl.contains("dashboard") && !currentUrl.contains("accounts")) {
            driver.get("https://monetis-delta.vercel.app/dashboard");
        }
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(menuTransfer));
        Assert.assertTrue(element.isDisplayed());
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        wait.until(ExpectedConditions.urlContains("transfer"));
        Assert.assertTrue(driver.getCurrentUrl().contains("transfer"));
    }

    public void goToAccountsSectionFromDashboard() {
        String currentUrl = driver.getCurrentUrl();
        if (!currentUrl.contains("dashboard") && !currentUrl.contains("accounts")) {
            driver.get("https://monetis-delta.vercel.app/dashboard");
        }
        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(menuAccounts));
        Assert.assertTrue("Accounts menu is not visible", element.isDisplayed());
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);
        wait.until(ExpectedConditions.urlContains("accounts"));
        Assert.assertTrue("Not on accounts page", driver.getCurrentUrl().contains("accounts"));
    }

    public void goToAccountsSectionFromTransfer() {
        String currentUrl = driver.getCurrentUrl();

        if (currentUrl.contains("accounts")) {
            try {
                wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loading_screen")));
            } catch (Exception ignored) {
            }
            Assert.assertTrue(driver.getCurrentUrl().contains("accounts"));
            return;
        }

        if (!currentUrl.contains("dashboard")) {
            driver.get("https://monetis-delta.vercel.app/dashboard");
            wait.until(ExpectedConditions.urlContains("dashboard"));
        }

        try {
            wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loading_screen")));
        } catch (Exception ignored) {
        }

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        WebElement element = new WebDriverWait(driver, Duration.ofSeconds(20))
                .until(ExpectedConditions.elementToBeClickable(menuAccounts));
        Assert.assertTrue(element.isEnabled());
        ((org.openqa.selenium.JavascriptExecutor) driver).executeScript("arguments[0].click();", element);

        wait.until(ExpectedConditions.urlContains("accounts"));
        Assert.assertTrue(driver.getCurrentUrl().contains("accounts"));
    }



    public void selectOwnAccountOption() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loading_screen")));
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(optionOwnAccount));
        Assert.assertTrue("Own account option is not displayed", option.isDisplayed());
        option.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(selectAccount));
    }

    public void selectSavingsAccount(String accountName) {
        wait.until(ExpectedConditions.presenceOfElementLocated(selectAccount));
        WebElement selectElem = wait.until(ExpectedConditions.elementToBeClickable(selectAccount));
        selectElem.click();

        wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@role='listbox']")));

        String xpathOption = "//div[@role='option' and contains(translate(text(),'ABCDEFGHIJKLMNOPQRSTUVWXYZ','abcdefghijklmnopqrstuvwxyz'),'" + accountName.toLowerCase() + "')]";
        WebElement option = wait.until(ExpectedConditions.elementToBeClickable(By.xpath(xpathOption)));
        option.click();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }


    public void fillTransferAmount(String amount) {
        wait.until(ExpectedConditions.visibilityOfElementLocated(amountTransfer)).clear();
        driver.findElement(amountTransfer).sendKeys(amount);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void clickNextButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(btnNext));
        button.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public void clickNextConfirmButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(btnNextConfirm));
        button.click();
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }
    public void clickCloseButton() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(btnClose));
        button.click();
    }

    public void verifyConfirmationWindow() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(btnNextConfirm));
    }

    public void captureBalance(String accountName) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(initialBalance(accountName)));
        String balanceText = element.getText().replaceAll("[^0-9.,]", "").replace(".", "").replace(",", ".");
        previousBalance = Double.parseDouble(balanceText);
    }

    public void captureFinalBalance(String accountName) {
        WebElement element = wait.until(ExpectedConditions.visibilityOfElementLocated(lastBalance(accountName)));
        String balanceText = element.getText().replaceAll("[^0-9.,]", "").replace(".", "").replace(",", ".");
        finalBalance = Double.parseDouble(balanceText);
    }

    public void verifySuccessAndClose() {
        clickCloseButton();
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
    }

    public void verifyBalanceIncreased(String accountName) {
        Assert.assertTrue("Balance did not increase: previous=" + previousBalance + " final=" + finalBalance, finalBalance > previousBalance);
    }
}
