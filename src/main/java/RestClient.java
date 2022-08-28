import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

public class RestClient {

    private static final String BASE_URL = "https://qa-scooter.praktikum-services.ru/";

        public RequestSpecification getBaseSpec() {
            return new RequestSpecBuilder()
                    .setContentType(ContentType.JSON)
                    .setBaseUri(BASE_URL)
                    .build();
        }
            //Требуется доработка
//        public RequestSpecification getOrderSpec() {
//            return new RequestSpecBuilder()
//                    .setContentType(ContentType.JSON)
//                    .setBaseUri(BASE_URL)
//                    .build();
//        }
}