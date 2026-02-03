package utils;

import hooks.Hooks;
import io.restassured.RestAssured;
import io.restassured.response.Response;

public class ApiUtils {
    public static String getIbanByEmail(String email) {
        String username = "john@email.com";
        String password = "atec123-";
        Response response = RestAssured.given()
                .auth()
                .basic(username, password)
                .queryParam("email", email)
                .when()
                .get("https://monetis-delta.vercel.app/api/users/api/getIbanByEmail")
                .then()
                .statusCode(200)
                .extract().response();
        return response.jsonPath().getString("[0].iban");
    }

    public static void createAccount(String account, int amount) {
        String username = "john@email.com";
        String password = "atec123-";
        RestAssured.given()
                .header("x-username", username)
                .header("x-password", password)
                .contentType("application/json")
                .body("{\"account\":\"" + account + "\", \"amount\":" + amount + "}")
                .when()
                .post("https://monetis-delta.vercel.app/api/accounts/api/createAccount")
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
                    "password": "thisIsMyPassword!1",
                    "confirmPassword": "thisIsMyPassword!1"
                }
                """.formatted(email);
        RestAssured.given()
                .contentType("application/json")
                .body(requestBody)
                .when()
                .post("https://monetis-delta.vercel.app/api/users/register")
                .then()
                .log().all()
                .statusCode(200);
    }

    public static void addMoney(int amount) {
        RestAssured.given()
                .header("x-username", Hooks.getTestEmail())
                .header("x-password", "thisIsMyPassword!1")
                .contentType("application/json")
                .body("{\"amount\": " + amount + "}")
                .when()
                .post("https://monetis-delta.vercel.app/api/users/api/addMoney")
                .then()
                .log().all()
                .statusCode(200);
    }
}
