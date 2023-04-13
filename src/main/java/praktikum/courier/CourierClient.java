package praktikum.courier;

import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import praktikum.data.Courier;
import praktikum.data.CourierCreds;
import praktikum.spec.ScooterRentSpec;

import static io.restassured.RestAssured.given;
import static org.hamcrest.CoreMatchers.is;

public class CourierClient extends ScooterRentSpec {

    private static final String PATH = "api/v1/courier";
    private static final String LOGIN_PATH = "api/v1/courier/login";
    private static final String API_DELETE = "/api/v1/courier/";


    public CourierClient() {
        RestAssured.baseURI = BASE_URI;
    }

    @Step("создание курьера")
    public ValidatableResponse create(Courier courier) {
        return given()
                .spec(ScooterRentSpec.requestSpecification())
                .and()
                .body(courier)
                .when()
                .post(PATH)
                .then()
                .assertThat()
                .body("ok", is (true));
    }

  @Step("регистрация курьера")
    public ValidatableResponse login(CourierCreds creds) {
        return given()
                .spec(ScooterRentSpec.requestSpecification())
                .and()
                .body(creds)
                .when()
                .post(LOGIN_PATH)
                .then();
    }

    @Step("удаление курьера")
    public ValidatableResponse courierDelete(String id) {
        return given()
                .spec(ScooterRentSpec.requestSpecification())
                .delete(API_DELETE + id)
                .then();
    }



}