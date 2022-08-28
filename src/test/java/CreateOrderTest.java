import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

//Тест не дописан
@RunWith(Parameterized.class)
public class CreateOrderTest {
    private Order order;

//    @Before
//    public void setUp() {
//        order = OrderGenerator.getDefault();
//    }

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
    public void createOrder() {

    }

}
