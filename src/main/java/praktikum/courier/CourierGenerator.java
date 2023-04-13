package praktikum.courier;

import praktikum.data.Courier;

import static praktikum.utils.Utils.randomString;

public class CourierGenerator {

    public static Courier randomCourier() {
        return new Courier()
                .setLogin(randomString(5))
                .setPassword(randomString(8))
                .setFirstName(randomString(10));
    }
}
