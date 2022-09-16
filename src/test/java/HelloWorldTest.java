import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HelloWorldTest {

    @Test
    public void helloYou(){
        Map<String, String> params = new HashMap<>();
        params.put("name", "Olia");
        Response response = RestAssured
                .given()
                .queryParams(params)
                .get("https://playground.learnqa.ru/api/hello").andReturn();
        response.prettyPrint();
    }

    @Test
    public void ex5Test(){
        JsonPath response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/get_json_homework")
                        .jsonPath();
        String message = response.get("messages[1].message");
        System.out.println(message);
    }

    @Test
    public void ex6Test(){
        Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();
        System.out.println(response.getHeader("location"));
    }

    @Test
    public void ex7Test(){
        Response response = RestAssured
                .given().redirects().follow(false)
                .relaxedHTTPSValidation()
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();
        System.out.println(response.getHeaders());
        System.out.println(response.getStatusCode());
    }
}
