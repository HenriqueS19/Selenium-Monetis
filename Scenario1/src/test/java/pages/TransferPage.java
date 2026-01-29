package pages;

import io.cucumber.java.en.When;
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

    // seletors
    private By menuTransfer = By.xpath("//[contains(text(),'Transfer')]");
    private By optionOwnAccount = By.xpath("//*[contains(text(),'Own Account')]");
    private By selectAccount = By.xpath("(//input[contains(@id,'react-select') and contains(@id,'-input')])[2]");
    private By amountTransfer = By.name("amount");
    private By btnNext = By.xpath("//button[text()='Next']");
    private By btnNextConfirm = By.xpath("//button[@type='submit' and text()='Next']");
    private By btnClose = By.xpath("//button[@type='submit' and text()='Close']");
    private By confirmationWindow = By.xpath("//div[contains(@class,'confirmation')]");
    private By successPage = By.xpath("//div[contains(text(),'Transfer successful')]");

    // Constructor
    public TransferPage(WebDriver driver) {
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

    public void selectOwnAccountOption() {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(By.className("loading_screen")));
        wait.until(ExpectedConditions.elementToBeClickable(optionOwnAccount)).click();
        // Aguardar o formulário carregar
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
       // wait.until(ExpectedConditions.urlContains("dashboard"));
    }

    public void verifyConfirmationWindow() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(btnNextConfirm));
    }

    public void verifySuccessPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(successPage));
    }

    public void verifySuccessAndClose() {
        verifySuccessPage();
        clickCloseButton();
    }

}
