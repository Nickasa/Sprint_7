import io.qameta.allure.Step;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;

public class OrderClient extends RestClient{
    private final static String ORDER_PATH = "/api/v1/orders";

    @Step("Create new order {order}")
    public ValidatableResponse create(Order order) {
        return given()
                .spec(getBaseSpec())
                .when()
                .body(order)
                .post(ORDER_PATH)
                .then();
    }

    @Step("Get order list")
    public ValidatableResponse get() {
        return given()
                .spec(getBaseSpec())
                .when()
                .get(ORDER_PATH)
                .then();
    }
}
