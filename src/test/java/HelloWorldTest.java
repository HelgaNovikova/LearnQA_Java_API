import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

public class HelloWorldTest {

    @Test
    public void helloYou() {
        Map<String, String> params = new HashMap<>();
        params.put("name", "Olia");
        Response response = RestAssured
                .given()
                .queryParams(params)
                .get("https://playground.learnqa.ru/api/hello").andReturn();
        response.prettyPrint();
    }

    @Test
    public void ex5Test() {
        JsonPath response = RestAssured
                .given()
                .get("https://playground.learnqa.ru/api/get_json_homework")
                .jsonPath();
        String message = response.get("messages[1].message");
        System.out.println(message);
    }

    @Test
    public void ex6Test() {
        Response response = RestAssured
                .given()
                .redirects()
                .follow(false)
                .get("https://playground.learnqa.ru/api/long_redirect")
                .andReturn();
        System.out.println(response.getHeader("location"));
    }

    @Test
    public void ex7Test() {
        int statusCode;
        String location = "https://playground.learnqa.ru/api/long_redirect";
        int numberOfRedirects = 0;
        do {
            Response response = RestAssured
                    .given()
                    .redirects()
                    .follow(false)
                    .get(location)
                    .andReturn();
            statusCode = response.getStatusCode();
            if (statusCode != 200) {
                numberOfRedirects++;
                location = response.getHeader("location");
            }
        }
        while (statusCode != 200);
        System.out.println("numberOfRedirects = " + numberOfRedirects);
    }

    @Test
    public void ex8Test() throws InterruptedException {
        JsonPath createTask = RestAssured
                .given()
                .redirects()
                .follow(false)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        String token = createTask.get("token");
        int seconds = createTask.get("seconds");

        String statusBeforeWait = sendTaskRequest(token).get("status");
        assertTrue(statusBeforeWait.equalsIgnoreCase("Job is NOT ready"));
        Thread.sleep(seconds * 1000);
        JsonPath getTask = sendTaskRequest(token);
        String statusAfterWait = getTask.get("status");
        assertTrue(statusAfterWait.equalsIgnoreCase("Job is ready"));
        String result = "";
        try{
            result = getTask.get("result");
        }
        catch (IllegalArgumentException e){
            System.out.println("Result field is not presented");
        }
        assertFalse(result.equals(""));
    }

    public JsonPath sendTaskRequest(String token){
        Map<String, String> params = new HashMap<>();
        params.put("token", token);
        JsonPath getTask = RestAssured
                .given()
                .queryParams(params)
                .get("https://playground.learnqa.ru/ajax/api/longtime_job")
                .jsonPath();
        return getTask;
    }
}
