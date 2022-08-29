import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;


public class CreateCourierTest {
    private Courier courier;
    private Courier courierWithoutCredentials;
    private Courier courierWithoutLogin;
    private Courier courierWithoutPassword;
    private CourierClient courierClient;
    private int courierId;

    @Before
    public void setUp() {
        courier = CourierGenerator.getDefault();
        courierClient = new CourierClient();
    }

    @After
    public void tearDown() {
        courierClient.delete(courierId);
    }

    @Test
    @DisplayName("Check courier can be created and response code/body")
    public void courierCanBeCreated () {
        ValidatableResponse response = courierClient.create(courier);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code incorrect", SC_CREATED, statusCode);

        boolean courierIsCreated = response.extract().path("ok");
        assertTrue("Courier not created", courierIsCreated);

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals("Courier login failed", SC_OK, loginStatusCode);

        courierId = loginResponse.extract().path("id");
    }

    //Тест на создание курьера
    @Test
    @DisplayName("Check courier cannot be created and response code/body")
    public void createExistingCourier() {
        ValidatableResponse response = courierClient.create(courier);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code incorrect", SC_CREATED, statusCode);

        ValidatableResponse existingCourierResponse = courierClient.create(courier);
        int existingCourierStatusCode = existingCourierResponse.extract().statusCode();
        assertEquals("Wrong code", SC_CONFLICT, existingCourierStatusCode);

        String existingCourierMessage = existingCourierResponse.extract().path("message");
        assertEquals("Wrong message", "Этот логин уже используется. Попробуйте другой.", existingCourierMessage);

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));
        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals("Courier login failed", SC_OK, loginStatusCode);

        courierId = loginResponse.extract().path("id");
    }

    //Тест на проверку невозможности создания курьера с разным набором обязательных полей, не стал разносить в отдельные тесты проверку на каждое поле, поскольку метод один, так же не стал делать проверку для firstName,
    //поле не обязательное, так как без него создается курьер, и в документации к API указано, что запрос выдает ошибку только при отсутствии логина или пароля
    @Test
    public void createCourierWithoutRequiredFields() {
        courierWithoutCredentials = CourierGenerator.getEmptyCredentials();
        courierWithoutLogin = CourierGenerator.getWithoutLogin();
        courierWithoutPassword = CourierGenerator.getWithoutPassword();

        //Создание курьера без полей невозможно
        ValidatableResponse responseWithoutCredentials = courierClient.create(courierWithoutCredentials);

        int statusCodeWithoutCredentials = responseWithoutCredentials.extract().statusCode();
        assertEquals("Wrong status code", SC_BAD_REQUEST, statusCodeWithoutCredentials);

        String errorMessageWithoutCredentials = responseWithoutCredentials.extract().path("message");
        assertEquals("Wrong message", "Недостаточно данных для создания учетной записи", errorMessageWithoutCredentials);

        //Создание курьера без логина невозможно
        ValidatableResponse responseWithoutLogin = courierClient.create(courierWithoutLogin);

        int statusCodeWithoutLogin = responseWithoutLogin.extract().statusCode();
        assertEquals("Wrong status code", SC_BAD_REQUEST, statusCodeWithoutLogin);

        String errorMessageWithoutLogin = responseWithoutCredentials.extract().path("message");
        assertEquals("Wrong message", "Недостаточно данных для создания учетной записи", errorMessageWithoutLogin);

        //Создание курьера без пароля невозможно
        ValidatableResponse responseWithoutPassword = courierClient.create(courierWithoutPassword);

        int statusCodeWithoutPassword = responseWithoutPassword.extract().statusCode();
        assertEquals("Wrong status code", SC_BAD_REQUEST, statusCodeWithoutPassword);

        String errorMessageWithoutPassword = responseWithoutPassword.extract().path("message");
        assertEquals("Wrong message", "Недостаточно данных для создания учетной записи", errorMessageWithoutPassword);
    }
}
