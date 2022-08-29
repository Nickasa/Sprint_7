import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class CourierClient extends RestClient {

    private final static String COURIER_PATH = "/api/v1/courier";
    private final static String COURIER_LOGIN_PATH = "/api/v1/courier/login";
    private final static String COURIER_DELETE_PATH = "/api/v1/courier/";

    @Step("Create new courier {courier}")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(getBaseSpec())
                .when()
                .body(courier)
                .post(COURIER_PATH)
                .then();
    }

    @Step("Login courier with credentials {credentials}")
    public ValidatableResponse login(CourierCredentials credentials) {
        return given()
                .spec(getBaseSpec())
                .when()
                .body(credentials)
                .post(COURIER_LOGIN_PATH)
                .then();
    }

    @Step("Delete courier with id {id}")
    public ValidatableResponse delete(int courierId) {

        return given()
                .spec(getBaseSpec())
                .when()
                .delete(COURIER_DELETE_PATH + courierId)
                .then();

    }
}
