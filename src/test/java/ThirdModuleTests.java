import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.Before;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

public class ThirdModuleTests {

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

    public static Stream<Arguments> dataSet() {
        return Stream.of(
                Arguments.of("Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
                        "Mobile", "No", "Android"),
                Arguments.of("Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1",
                        "Mobile", "Chrome", "iOS"),
                Arguments.of("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)",
                        "Googlebot", "Unknown", "Unknown"),
                Arguments.of("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0",
                        "Web", "Chrome", "No"),
                Arguments.of("Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1",
                        "Mobile", "No", "iPhone")
        );
    }

//    public static UserAgentsCheck[] dataSet2(){
//        return new UserAgentsCheck[]{
//                new UserAgentsCheck("Mozilla/5.0 (Linux; U; Android 4.0.2; en-us; Galaxy Nexus Build/ICL53F) AppleWebKit/534.30 (KHTML, like Gecko) Version/4.0 Mobile Safari/534.30",
//                        "Mobile", "No", "Android"),
//                new UserAgentsCheck("Mozilla/5.0 (iPad; CPU OS 13_2 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) CriOS/91.0.4472.77 Mobile/15E148 Safari/604.1",
//                        "Mobile", "Chrome", "iOS"),
//                new UserAgentsCheck("Mozilla/5.0 (compatible; Googlebot/2.1; +http://www.google.com/bot.html)",
//                        "Googlebot", "Unknown", "Unknown"),
//                new UserAgentsCheck("Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/91.0.4472.77 Safari/537.36 Edg/91.0.100.0",
//                        "Web", "Chrome", "No"),
//                new UserAgentsCheck("Mozilla/5.0 (iPad; CPU iPhone OS 13_2_3 like Mac OS X) AppleWebKit/605.1.15 (KHTML, like Gecko) Version/13.0.3 Mobile/15E148 Safari/604.1",
//                        "Mobile", "No", "iPhone")
//        };
//    }

//    @ParameterizedTest
//    @MethodSource(value = "dataSet2")
//    void testUserAgent(UserAgentsCheck dataSet) {
//        System.out.println(dataSet.getExpectedBrowser());
////        JsonPath getUserAgentData = RestAssured
////                .given()
////                .header("User Agent", dataSet[0])
////                .get("https://playground.learnqa.ru/ajax/api/user_agent_check")
////                .jsonPath();
////
////        String actualPlatform = getUserAgentData.get("platform");
////        String actualBrowser = getUserAgentData.get("browser");
////        String actualDevice = getUserAgentData.get("device");
////
////        assertEquals(actualPlatform, dataSet[1]);
//    }

    private String actualPlatform;
    private String actualBrowser;
    private String actualDevice;

    @BeforeEach
    @ParameterizedTest
    @MethodSource(value = "dataSet")
    public void testUserAgent(String userAgent, String expectedPlatform, String expectedBrowser, String expectedDevice) {
        JsonPath getUserAgentData = RestAssured
                .given()
                .header("User-Agent", userAgent)
                .get("https://playground.learnqa.ru/ajax/api/user_agent_check")
                .jsonPath();
        String actualPlatform = getUserAgentData.get("platform");
        String actualBrowser = getUserAgentData.get("browser");
        String actualDevice = getUserAgentData.get("device");
        assertEquals(expectedPlatform, actualPlatform);
       // assertEquals(expectedBrowser, actualBrowser);
        //assertEquals(expectedDevice, actualDevice);
    }

    @ParameterizedTest
    @MethodSource(value = "dataSet")
    public void checkBrowser(String userAgent, String expectedPlatform, String expectedBrowser, String expectedDevice){
        assertEquals(expectedBrowser, actualBrowser);
    }


}
