import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import java.util.ArrayList;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GetOrdersTest {
    private OrderClient orderClient;


    @Test
    @DisplayName("Check orders list can be received and response code/body")
    public void getOrders(){
        OrderClient orderClient = new OrderClient();
        ValidatableResponse response = orderClient.get();

        int responseCode = response.extract().statusCode();
        assertEquals("Status code incorrect", SC_OK, responseCode);

        ArrayList ordersExisting = response.extract().path("orders");
        assertNotNull("Id was not received", ordersExisting);
    }
}
