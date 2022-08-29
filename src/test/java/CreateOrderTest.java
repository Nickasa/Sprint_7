import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.apache.http.HttpStatus.SC_CREATED;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

@RunWith(Parameterized.class)
public class CreateOrderTest {
    private final String[] colors;
    private final OrderClient orderClient = new OrderClient();
    public CreateOrderTest(String[] colors){
        this.colors = colors;

    }

    @Parameterized.Parameters
    public static Object[][] getColors() {
        return new Object[][]{
                {new String[]{"GRAY", "BLACK"}},
                {new String[]{"GRAY"}},
                {new String[]{"BLACK"}},
                {new String[]{}}
        };
    }

    @Test
    @DisplayName("Check orders can be created with or without color and response code/body")
    public void createOrderTest() {
        Order defaultOrder = OrderGenerator.getDefault(colors);
        ValidatableResponse response = orderClient.create(defaultOrder);

        int orderCreatedResponse = response.extract().statusCode();
        assertEquals("Wrong code", SC_CREATED, orderCreatedResponse);

        int trackId = response.extract().path("track");
        assertNotNull("Id was not received", trackId);
    }
}
