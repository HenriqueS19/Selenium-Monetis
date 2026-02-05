package utils;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import config.TestConfig;
import java.time.Duration;

public class WaitUtils {

    private WebDriver driver;
    private WebDriverWait defaultWait;
    private WebDriverWait longWait;

    public WaitUtils(WebDriver driver) {
        this.driver = driver;
        this.defaultWait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.DEFAULT_TIMEOUT));
        this.longWait = new WebDriverWait(driver, Duration.ofSeconds(TestConfig.LONG_TIMEOUT));
    }

    public WebElement waitForElementToBeClickable(By locator) {
        return defaultWait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public WebElement waitForElementToBeVisible(By locator) {
        return defaultWait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    public void waitForElementToBeInvisible(By locator) {
        defaultWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
    }

    public WebElement waitForElementToBeClickableLong(By locator) {
        return longWait.until(ExpectedConditions.elementToBeClickable(locator));
    }

    public boolean waitForUrlContains(String urlPart) {
        return defaultWait.until(ExpectedConditions.urlContains(urlPart));
    }

    public void waitForElementToDisappear(By locator) {
        try {
            defaultWait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
        } catch (Exception ignored) {
        }
    }

    public WebElement waitForElementPresence(By locator) {
        return defaultWait.until(ExpectedConditions.presenceOfElementLocated(locator));
    }
}