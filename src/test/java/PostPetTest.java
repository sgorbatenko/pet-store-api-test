import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import models.PetDto;
import models.Status;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;

public class PostPetTest extends BaseTest {
    @Test
    @DisplayName("Pet could be created with valid data")
    public void testPostPet() {
        Long id = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);

        PetDto.Pet pet = PetDto.Pet.builder().id(id.toString()).name("Adel")
                .category(new PetDto.Category("1", "test"))
                .photoUrls(new ArrayList<>(2))
                .status(Status.AVAILABLE)
                .build();

        Response response = given().log().all()
                .contentType("application/json")
                .body(pet)
                .when()
                .post("/pet");

        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals(pet, response.getBody().as(PetDto.Pet.class));
    }

    @Test
    @DisplayName("Pet could not be created with invalid input")
    public void testPostPetInvalid() {
        given().log().all()
                .contentType("application/json")
                .body("")
                .when()
                .post("/pet")
                .then()
                .statusCode(405);
    }
}
