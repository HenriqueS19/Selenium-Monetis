package utils;

import config.TestConfig;
import hooks.Hooks;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.junit.Test;


public class ApiUtils {
    public static String getIbanByEmail(String email) {
        Response response = RestAssured.given()
                .auth()
                .basic(TestConfig.TEST_USER_EMAIL, TestConfig.TEST_USER_PASSWORD)
                .queryParam("email", email)
                .when()
                .get(TestConfig.API_GET_IBAN)
                .then()
                .statusCode(200)
                .extract().response();
        return response.jsonPath().getString("[0].iban");
    }

    public static void createAccount(String account, int amount) {
        RestAssured.given()
                .header("x-username", TestConfig.TEST_USER_EMAIL)
                .header("x-password", TestConfig.TEST_USER_PASSWORD)
                .contentType("application/json")
                .body("{\"account\":\"" + account + "\", \"amount\":" + amount +"}")
                .when()
                .post(TestConfig.API_CREATE_ACCOUNT)
                .then()
                .statusCode(200);
    }

    public static void registerTestUser(String email) {
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
                    "password": "%s",
                    "confirmPassword": "%s"
                }
                """.formatted(email,TestConfig.NEW_USER_PASSWORD, TestConfig.NEW_USER_PASSWORD);
        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post(TestConfig.API_REGISTER_USER)
                .then()
                .log().all()
                .statusCode(200);
    }

    public static void addMoney(int amount) {
        RestAssured.given()
                .header("x-username", Hooks.getTestEmail())
                .header("x-password", TestConfig.NEW_USER_PASSWORD)
                .contentType("application/json")
                .body("{\"amount\":" + amount + "}")
                .when()
                .post(TestConfig.API_ADD_MONEY)
                .then()
                .log().all()
                .statusCode(200);
    }
}
