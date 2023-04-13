package praktikum.order;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import praktikum.spec.ScooterRentSpec;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.notNullValue;

public class OrderCheck extends ScooterRentSpec{

    public OrderCheck() {
        RestAssured.baseURI = BASE_URI;
    }

  @Step("создание заказа ")
    public ValidatableResponse createOrderTest(Order order) {
        return given()
                .spec(ScooterRentSpec.requestSpecification())
                .post("api/v1/orders")
                .then()
                .assertThat()
                .body("track", notNullValue());
    }

    @Step("получение списка заказов")
    public ValidatableResponse getListOrder(){
        Response response = given().get("api/v1/orders");
        return response.then().assertThat().body("orders",notNullValue());
    }



}
