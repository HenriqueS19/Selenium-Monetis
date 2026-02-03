package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import io.github.bonigarcia.wdm.WebDriverManager;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import org.hamcrest.Matcher;
import org.hamcrest.Matchers;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import utils.ApiUtils;

import java.time.Duration;

public class Hooks {
    private static WebDriver driver;
    private static String testEmail;

    @BeforeAll
    public static void TLSForRestAssured() {
        RestAssured.config = RestAssured.config().sslConfig(new SSLConfig().relaxedHTTPSValidation());
    }

    @Before(order = 1)
    public void registerTestUs() {
        testEmail = "testingaccount" + java.util.UUID.randomUUID() + "@test.com";
        ApiUtils.registerTestUser(testEmail);
    }
    
    @Before(order =2)
    public void setup() {
        // Automatically downloads and manages the correct chromedriver version
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized"); // browser in full screen
        options.addArguments("--remote-allow-origins=*"); // Prevents some common connection errors
        driver = new ChromeDriver(options);
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @After
    public void tearDown() {
        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }
        if (driver != null) {
            driver.quit();
        }
    }

    public static WebDriver getDriver() {
        return driver;
    }

    public static String getTestEmail() {
        return testEmail;
    }
}

