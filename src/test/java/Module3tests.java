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
    public void ex10Test(String str){
        assertTrue(str.length() > 15, "length of the string is less then 15 symbols");
    }
}
