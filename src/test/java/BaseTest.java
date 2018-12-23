import io.restassured.RestAssured;
import utils.TestPropertiesLoader;

public class BaseTest {
    static {
        RestAssured.baseURI = TestPropertiesLoader.getBaseUrl();
    }
}
