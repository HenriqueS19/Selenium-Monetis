package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import static io.restassured.RestAssured.given;
import java.time.Duration;

public class Hooks {
    private static WebDriver driver;

    @Before
    public void registerTestUser() {
        String requestBody = """
                {  
                    "name": "Testing",
                    "surname": "Account",
                    "email": "testingaccount@test.com",
                    "phone_number": "123123123",
                    "street_address": "Some Random street",
                    "postal_code": "1231-123",
                    "city": "Lisbon",
                    "country": "PT",
                    "password": "testingPassword!1",
                    "confirmPassword": "testingPassword!1"
                }
                """;
        given()
            .contentType("application/json")
            .body(requestBody)
            .when()
                .post("https://monetis-delta.vercel.app/api/users/register")
                .then()
                .statusCode(201);

    }               
    
    @Before
    public void setup() {
        // Automatically downloads and manages the correct chromedriver version
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized"); // browser in full screen
        options.addArguments("--remote-allow-origins=*"); // Prevents some common connection errors

        driver = new ChromeDriver(options);

        // Safety net: waitups 5 seconds if an element isn´t immediately found
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
    }

    @After
    public void tearDown() {
        // Closes the browser after the scenario is finished ( Success or failure)
        if (driver != null) {
         //   driver.quit();
        }

    }

    // this method allows LoginSteps to borrow the driver created here
    public static WebDriver getDriver() {
            return driver;
    }
}

