package praktikum;

import io.qameta.allure.Description;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.RestAssured;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import praktikum.courier.CourierClient;
import praktikum.data.Courier;
import praktikum.data.CourierCreds;
import praktikum.spec.ScooterRentSpec;

import static org.apache.http.HttpStatus.SC_BAD_REQUEST;
import static org.apache.http.HttpStatus.SC_NOT_FOUND;
import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertEquals;
import static praktikum.courier.CourierGenerator.randomCourier;
import static praktikum.data.CourierCreds.credsFrom;

public class CourierTests {

    private CourierClient courierClient;
    private Courier courier;
    private String courierId;

    @Before
    public void setUp() {
        courier = randomCourier();
        courierClient = new CourierClient();
        RestAssured.baseURI = ScooterRentSpec.BASE_URI;
    }

    @Test
    @DisplayName("регистрация нового курьера")
    @Description("проверка создания курьера с валидными данными")
    public void createCourier() {
        ValidatableResponse response = courierClient.create(courier);
        assertEquals("Статус код неверный при создании курьера или неверный ответ в теле",
                HttpStatus.SC_CREATED, response.extract().statusCode());
    }

    @Test
    @DisplayName("регистрация нового курьера")
    @Description("проверка авторизации курьера с валидными данными")
    public void loginCourier() {
        courierClient.create(courier);
        ValidatableResponse loginResponse = courierClient.login(credsFrom(courier));
        courierId = loginResponse.extract().path("id").toString();
        assertEquals("Статус код неверный при попытке логина созданным курьером",
                HttpStatus.SC_OK, loginResponse.extract().statusCode());
    }

    @Test
    @DisplayName("регистрация нового курьера")
    @Description("проверка создания 2 одинаковых курьеров")
    public void createSameCourier() {
        courierClient.create(courier);
        ValidatableResponse createTheSameResponse = courierClient.create(courier);
        assertEquals("Статус код неверный при попытке создать одинакового курьера",
                HttpStatus.SC_CONFLICT, createTheSameResponse.extract().statusCode());
    }

    @Test
    @DisplayName("логин нового курьера")
    @Description("проверка авторизации курьера с существующим логином")
    public void loginSameCourier() {
        courierClient.create(courier);
        courierClient.login(credsFrom(courier));
        ValidatableResponse theSameLogin = courierClient.login(new CourierCreds(courier.getLogin(), "1234"));
        assertEquals("Статус код неверный при попытке залогинить курьера с одинаковым логином",
                SC_NOT_FOUND, theSameLogin.extract().statusCode());
    }

    @Test
    @DisplayName("Авторизация курьера без логина")
    @Description("Проверка, что курьер не может авторизоваться без логина")
    public void NoLoginCourierWithoutLoginTest() {
        courierClient.create(courier);
        Courier courier1 = new Courier(null,courier.getPassword(),courier.getFirstName());
        ValidatableResponse loginResponse = courierClient.login(credsFrom(courier1));
        loginResponse.assertThat().statusCode(SC_BAD_REQUEST).and().body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера без пароля")
    @Description("Проверка, что курьер не может авторизоваться пароля")
    public void NoLoginCourierWithoutPasswordTest() {
        courierClient.create(courier);
        Courier courier1 = new Courier(courier.getLogin(),null,courier.getFirstName());
        ValidatableResponse loginResponse = courierClient.login(credsFrom(courier1));
        loginResponse.assertThat().statusCode(SC_BAD_REQUEST).and().body("message", is("Недостаточно данных для входа"));
    }

    @Test
    @DisplayName("Авторизация курьера с неправильным паролем")
    @Description("Проверка, что курьер не может авторизоваться с неправильным паролем")
    public void NoLoginCourierWithWrongPasswordTest() {
        courierClient.create(courier);
        Courier courier1 = new Courier(courier.getLogin(),"88",courier.getFirstName());
        ValidatableResponse loginResponse = courierClient.login(credsFrom(courier1));
        loginResponse.assertThat().statusCode(SC_NOT_FOUND).and().body("message", is("Учетная запись не найдена"));
    }

    @Test
    @DisplayName("Авторизация курьера с неправильным логином")
    @Description("Проверка, что курьер не может авторизоваться с логином паролем")
    public void NoLoginCourierWithWrongLoginTest() {
        courierClient.create(courier);
        Courier courier1 = new Courier("44",courier.getPassword(),courier.getFirstName());
        ValidatableResponse loginResponse = courierClient.login(credsFrom(courier1));
        loginResponse.assertThat().statusCode(SC_NOT_FOUND).and().body("message", is("Учетная запись не найдена"));
    }


    @After
    public void tearDown() {
        courierClient.courierDelete(courierId);
    }
}
