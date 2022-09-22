import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class Module3tests {

    @ParameterizedTest
    @ValueSource(strings = {"dkgdfg", "erjfn4569dkrudn", "erjfn4569dkrudn3"})
    public void ex10Test(String str) {
        assertTrue(str.length() > 15, "length of the string is less then 15 symbols");
    }

    @Test
    public void ex11Test() {
        Response requestForCookies = RestAssured
                .given()
                .redirects()
                .follow(false)
                .get("https://playground.learnqa.ru/api/homework_cookie")
                .andReturn();
        assertEquals("hw_value", requestForCookies.getCookie("HomeWork"), "cookie isn't correct");
    }

    @Test
    public void ex12Test() {
        Response requestForHeaders = RestAssured
                .given()
                .redirects()
                .follow(false)
                .get("https://playground.learnqa.ru/api/homework_header")
                .andReturn();
        assertEquals("Some secret value", requestForHeaders.getHeader("x-secret-homework-header"), "header isn't correct");
    }
}
