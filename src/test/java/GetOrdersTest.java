import io.restassured.response.ValidatableResponse;
import org.junit.Test;

import static org.apache.http.HttpStatus.SC_OK;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

public class GetOrdersTest {
    private OrderClient orderClient;

    //Тест не дописан
    @Test
    public void getOrders(){
        ValidatableResponse response = orderClient.get();

        int responseCode = response.extract().statusCode();
        assertEquals("Status code incorrect", SC_OK, responseCode);

//        courierId = loginResponse.extract().path("id");
//        assertNotNull("Id was not received", courierId);
    }
}
