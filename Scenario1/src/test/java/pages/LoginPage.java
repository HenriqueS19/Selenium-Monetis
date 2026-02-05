package pages;

import org.junit.Assert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import utils.WaitUtils;

public class LoginPage {

    private WebDriver driver;
    private WaitUtils waitUtils;

    // Selectors, lets map the page elements

    private By emailField = By.cssSelector("input[type='email'], input[name='email'], #email");
    private By passwordField = By.cssSelector("input[type='password'], input[name='password'], #password");
    private By loginButton = By.cssSelector("button[type='submit']");

    public LoginPage(WebDriver driver){
        this.driver = driver;
        this.waitUtils = new WaitUtils(driver);
    }

    public void fillCredentials(String email, String password) {
        WebElement emailInput = waitUtils.waitForElementToBeVisible(emailField);
        emailInput.clear();
        emailInput.sendKeys(email);
        Assert.assertEquals("Email field value does not match", email, emailInput.getAttribute("value"));

        WebElement passInput = waitUtils.waitForElementToBeVisible(passwordField);
        passInput.clear();
        passInput.sendKeys(password);
        Assert.assertEquals("Password field value does not match", password, passInput.getAttribute("value"));
    }

    public void accessPage(String url) {
        driver.get(url);
    }

    public void clickLogin() {
        WebElement button = waitUtils.waitForElementToBeClickable(loginButton);
        Assert.assertTrue("Login button is not enabled", button.isEnabled());
        button.click();
    }

    public void login(String email, String password) {
        fillCredentials(email, password);
        clickLogin();
        waitUtils.waitForUrlContains("dashboard");
    }

    public boolean isOnDashboard() {
        return waitUtils.waitForUrlContains("dashboard");
    }

}

