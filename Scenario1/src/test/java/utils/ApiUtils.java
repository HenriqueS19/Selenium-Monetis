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
}
