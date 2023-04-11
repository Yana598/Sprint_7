package praktikum;

import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import praktikum.order.Order;
import praktikum.order.OrderCheck;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class OrderTest {
    private OrderCheck orderCheck;
    private final String[] color;

    public OrderTest(String[] color) {
        this.color = color;
    }

    /**
     * параметризируем цвета самоката
     */
    @Parameterized.Parameters(name="{0}=no color,{1}=GREY,{2}=BLACK,{3}=TWO COLOR")
    public static Object[][] colorData() {
        Object[][] objects;
        objects = new Object[][]{
                {null},
                {new String[]{"GREY"}},
                {new String[]{"BLACK"}},
                {new String[]{"BLACK","GREY"}}

        };
        return objects;
    }
    @Before
    public void setUp() {
        orderCheck = new OrderCheck();
    }

    @Test
    public void createOrder() {
        Order order = new Order("Naruto",
                "Uchiha",
                "Konoha, 142 apt.",
                "4",
                "+7 800 355 35 35",
                5,
                "2020-06-06",
                "Saske, come back to Konoha",
                color);
        ValidatableResponse response = orderCheck.createOrderTest(order);

        assertEquals("Статус код неверный при создании заказа или не возвращается track",
                HttpStatus.SC_CREATED, response.extract().statusCode());

        ValidatableResponse responseList = orderCheck.getListOrder();

        assertEquals("Не приходит список заказов в ответ",
                HttpStatus.SC_OK, responseList.extract().statusCode());

    }

}