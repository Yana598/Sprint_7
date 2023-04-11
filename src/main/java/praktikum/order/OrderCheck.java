package praktikum.order;

import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderCheck {

    public static final String BASE_URI = "https://qa-scooter.praktikum-services.ru/";
    public OrderCheck() {
        RestAssured.baseURI = BASE_URI;
    }

    /**
     * создаем заказ,проверяем что в ответ пришел номер трека
     */
    public ValidatableResponse createOrderTest(Order order) {
        return given()
                .header("Content-type", "application/json")
                .and()
                .body(order)
                .when()
                .post("api/v1/orders")
                .then()
                .assertThat()
                .body("track", notNullValue());
    }

    /**
     * Проверяем, что в тело ответа возвращается список заказов.
     */
    public ValidatableResponse getListOrder(){
        Response response = given().get("api/v1/orders");
        return response.then().assertThat().body("orders",notNullValue());

    }

}
