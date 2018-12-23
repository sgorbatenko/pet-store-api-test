import io.qameta.allure.junit4.DisplayName;
import models.PetDto;
import models.Status;
import org.junit.Test;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

import static io.restassured.RestAssured.given;

public class DeletePetTest extends BaseTest {

    @Test
    @DisplayName("Pet could be deleted")
    public void testDeletePet() {
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
                .post("/pet");

        given().log().all()
                .contentType("application/json")
                .when()
                .delete("/pet/" + pet.getId())
                .then()
                .statusCode(200)
                .log().body();

        given().log().all()
                .contentType("application/json")
                .body("")
                .when()
                .get("/pet/" + pet.getId())
                .then()
                .statusCode(404)
                .log().body();
    }

    @Test
    @DisplayName("Not existing pet could not be deleted")
    public void testDeleteNotExistingPet() {
        Long randomId = ThreadLocalRandom.current().nextLong(0, Long.MAX_VALUE);
        given().log().all()
                .contentType("application/json")
                .when()
                .delete("/pet/" + randomId)
                .then()
                .statusCode(404)
                .log().body();
    }
}
