import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import models.PetDto;
import models.Status;
import org.junit.Assert;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;

public class UpdatePetTest extends BaseTest {
    @Test
    @DisplayName("Pet could be updated")
    public void testUpdatePet() {
        Long id = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);

        PetDto.Pet pet = PetDto.Pet.builder().id(id.toString()).name("Adel")
                .category(new PetDto.Category("1", "test"))
                .photoUrls(new ArrayList<>(2))
                .status(Status.AVAILABLE)
                .build();

        PetDto.Pet pet2 = pet.toBuilder().name("New name").build();

        given().log().all()
                .contentType("application/json")
                .body(pet)
                .when()
                .post("/pet")
                .then()
                .statusCode(200)
                .log().body();

        given().log().all()
                .contentType("application/json")
                .body(pet2)
                .when()
                .put("/pet")
                .then()
                .statusCode(200)
                .log().body();

        Response response = given().log().all()
                .contentType("application/json")
                .body("")
                .when()
                .get("/pet/" + pet.getId());
        Assert.assertEquals(200, response.statusCode());
        Assert.assertEquals(pet2, response.getBody().as(PetDto.Pet.class));
    }

    @Test
    @DisplayName("Not existing pet could not be updated")
    public void testUpdateNotExistingPet() {
        Long id = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);

        PetDto.Pet pet = PetDto.Pet.builder().id(id.toString()).name("Adel")
                .category(new PetDto.Category("1", "test"))
                .photoUrls(new ArrayList<>(2))
                .status(Status.AVAILABLE)
                .build();

        given().log().all()
                .contentType("application/json")
                .body(pet)
                .when()
                .put("/pet")
                .then()
                .statusCode(404)
                .log().body();
    }
}
