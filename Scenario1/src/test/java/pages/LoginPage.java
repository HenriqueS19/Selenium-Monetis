package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage {

    WebDriver driver;
    WebDriverWait wait;

    // Selectors, lets map the page elements

    private By emailField = By.cssSelector("input[type='email'], input[name='email'], #email");
    private By passwordField = By.cssSelector("input[type='password'], input[name='password'], #password");
    private By loginButton = By.cssSelector("button[type='submit']");

    // Constructor, receives the test driver to use on this page
    public LoginPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void fillCredentials(String email, String password) {
        // Wait for email field, clear it, and type
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        emailInput.clear();
        emailInput.sendKeys(email);

        // Wait for password field, clear it, and type
        WebElement passInput = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        passInput.clear();
        passInput.sendKeys(password);
    }

    // Actions methods
    public void accessPage(String url) {
        driver.get(url);
    }
    public void clickLogin() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        button.click();

    }

}

