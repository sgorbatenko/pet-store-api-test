import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import models.PetDto;
import models.Status;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;

public class GetPetTest extends BaseTest {
    @Test
    @DisplayName("Verify that pet could be retrieved by ID")
    public void testGetPetByValidId() {
        Long id = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);

        PetDto.Pet pet = PetDto.Pet.builder().id(id.toString()).name("Adel")
                .category(new PetDto.Category("1", "test"))
                .photoUrls(new ArrayList<String>(2))
                .status(Status.AVAILABLE)
                .build();

        given().log().all()
                .contentType("application/json")
                .body(pet)
                .when()
                .post("/pet");

        Response getResponse = given().log().all()
                .contentType("application/json")
                .body("")
                .when()
                .get("/pet/" + pet.getId());
        Assert.assertEquals(200, getResponse.statusCode());
        Assert.assertEquals(pet, getResponse.getBody().as(PetDto.Pet.class));
    }

    @Test
    @DisplayName("Pet must not be retrieved by invalid ID")
    public void testGetPetByInvalidId() {
        given().log().all()
                .contentType("application/json")
                .body("")
                .when()
                .get("/pet/$$$$")
                .then()
                .statusCode(400)
                .log().body();
    }

    @Test
    @DisplayName("Pet with not existing id could not be retrieved")
    public void testGetPetByNotExistingId() {
        Long id = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
        given().log().all()
                .contentType("application/json")
                .body("")
                .when()
                .get("/pet/" + id)
                .then()
                .statusCode(404)
                .log().body();
    }
}
