import io.restassured.response.ValidatableResponse;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.apache.http.HttpStatus.*;
import static org.junit.Assert.*;

public class LoginCourierTest {
    private Courier courier;
    private Courier courierLoginWithoutCredentials;
    private Courier courierLoginWithoutLogin;
    private Courier courierLoginWithoutPassword;
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
    public void courierCanLogin() {
        ValidatableResponse response = courierClient.create(courier);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code incorrect", SC_CREATED, statusCode);

        boolean courierIsCreated = response.extract().path("ok");
        assertTrue("Courier not created", courierIsCreated);

        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));

        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals("Status code incorrect", SC_OK, loginStatusCode);

        courierId = loginResponse.extract().path("id");
        assertNotNull("Id was not received", courierId);
    }

    //Проверка входа с частично заполненными полями
    @Test
    public void courierLoginWithWrongCredentials() {
        courierLoginWithoutCredentials = CourierGenerator.getEmptyCredentials();
        courierLoginWithoutLogin = CourierGenerator.getWithoutLogin();
        courierLoginWithoutPassword = CourierGenerator.getWithoutPassword();

        ValidatableResponse response = courierClient.create(courier);

        int statusCode = response.extract().statusCode();
        assertEquals("Status code incorrect", SC_CREATED, statusCode);

        boolean courierIsCreated = response.extract().path("ok");
        assertTrue("Courier not created", courierIsCreated);

        //Вход без логина и пароля
        ValidatableResponse responseLoginWithoutCredentials = courierClient.login(CourierCredentials.from(courierLoginWithoutCredentials));

        int statusCodeWithoutCredentials = responseLoginWithoutCredentials.extract().statusCode();
        assertEquals("Credentials missing", SC_BAD_REQUEST, statusCodeWithoutCredentials);

        String errorMessageWithoutCredentials = responseLoginWithoutCredentials.extract().path("message");
        assertEquals("Wrong message", "Недостаточно данных для входа", errorMessageWithoutCredentials);

        //Вход без логина
        ValidatableResponse responseLoginWithoutLogin = courierClient.login(CourierCredentials.from(courierLoginWithoutLogin));

        int statusCodeWithoutLogin = responseLoginWithoutLogin.extract().statusCode();
        assertEquals("Credentials missing", SC_BAD_REQUEST, statusCodeWithoutLogin);

        String errorMessageWithoutLogin = responseLoginWithoutLogin.extract().path("message");
        assertEquals("Wrong message", "Недостаточно данных для входа", errorMessageWithoutLogin);

        //Вход без пароля
        ValidatableResponse responseLoginWithoutPassword = courierClient.login(CourierCredentials.from(courierLoginWithoutPassword));

        int statusCodeWithoutPassword = responseLoginWithoutPassword.extract().statusCode();
        assertEquals("Credentials missing", SC_BAD_REQUEST, statusCodeWithoutPassword);

        String errorMessageWithoutPassword = responseLoginWithoutPassword.extract().path("message");
        assertEquals("Wrong message", "Недостаточно данных для входа", errorMessageWithoutPassword);

        //Успешный логин возвращает id, который так же используется для удаления курьера
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));

        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals("Status code incorrect", SC_OK, loginStatusCode);

        courierId = loginResponse.extract().path("id");
        assertNotNull("Id was not received", courierId);
    }

    //Логин с несуществующими учетными данными
    @Test
    public void loginWithNotExistingCredentials() {
        ValidatableResponse loginResponse = courierClient.login(CourierCredentials.from(courier));

        int loginStatusCode = loginResponse.extract().statusCode();
        assertEquals("Status code incorrect", SC_NOT_FOUND, loginStatusCode);

        String errorMessage = loginResponse.extract().path("message");
        assertEquals("Wrong message", "Учетная запись не найдена", errorMessage);
    }


}
