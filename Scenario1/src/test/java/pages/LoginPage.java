package pages;

import org.junit.Assert;
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

    public LoginPage(WebDriver driver){
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }

    public void fillCredentials(String email, String password) {
        WebElement emailInput = wait.until(ExpectedConditions.visibilityOfElementLocated(emailField));
        emailInput.clear();
        emailInput.sendKeys(email);
        Assert.assertEquals("Email field value does not match", email, emailInput.getAttribute("value"));

        WebElement passInput = wait.until(ExpectedConditions.visibilityOfElementLocated(passwordField));
        passInput.clear();
        passInput.sendKeys(password);
        Assert.assertEquals("Password field value does not match", password, passInput.getAttribute("value"));
    }

    public void accessPage(String url) {
        driver.get(url);
    }
    public void clickLogin() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(loginButton));
        Assert.assertTrue("Login button is not enabled", button.isEnabled());
        button.click();

    }

}

