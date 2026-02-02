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
        String requestBody = """
                {
                    "name": "Testing",
                    "surname": "Account",
                    "email": "%s",
                    "phone_number": "123123123",
                    "street_address": "Some Random street",
                    "postal_code": "1231-123",
                    "city": "Lisbon",
                    "country": "PT",
                    "password": "thisIsMyPassword!1",
                    "confirmPassword": "thisIsMyPassword!1"
                }
                """.formatted(testEmail);
        RestAssured.given()
            .contentType("application/json")
            .body(requestBody)
            .when()
                .post("https://monetis-delta.vercel.app/api/users/register")
                .then()
                .log().all()
                .statusCode(200);

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
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        if (driver != null) {
            driver.quit();
        }
    }

    // this method allows LoginSteps to borrow the driver created here
    public static WebDriver getDriver() {
        return driver;
    }

    public static String getTestEmail() {
        return testEmail;
    }
}

