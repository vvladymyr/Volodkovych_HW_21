import io.restassured.response.Response;
import models.Category;
import models.Pet;
import org.assertj.core.api.SoftAssertions;
import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

public class RefacTestsPetStore_6 {

    @Test
    public void verifyBody() {
        new PetStorePetEndPoint()
                .getPetByStatus("available")
                .then()
                .assertThat()
                //.log().body()
                .body(Matchers.notNullValue());
    }

    @Test
    public void verifyExistingPetReturn200() {
        new PetStorePetEndPoint()
                .getPetById("1")
                .then()
                .log().body()
                .statusCode(200);
    }
    @Test
    public void verifyNotExistingPetReturn404() {
        new PetStorePetEndPoint()
                .getPetById("123456789012345")
                .then()
                .log().body()
                .statusCode(404);
    }

    @Test
    public void verifyPetCanBeCreated() {
        Category category = new Category();
        category.setName("Cats");
        category.setId(123123);

        Pet cat = new Pet();
        cat.setName("Murchyk33");
        cat.setCategory(category);
        cat.setStatus("available");

        new PetStorePetEndPoint()
                .createPet(cat)
                .then()
                .statusCode(200);

        System.out.println();
    }

    @Test
    public void verifyPetHasIdAfterCreation() {
        Pet murchyk = Pet.createCatPetAvailable(123124, "Murchyk2");

        Response petResponse = new PetStorePetEndPoint()
                .createPet(murchyk);

        Pet petFromService = petResponse.body().as(Pet.class);
        Assert.assertNotNull(petFromService);

        Assert.assertNotNull(petFromService.getId());

        long idFromService = petFromService.getId();

        new PetStorePetEndPoint()
                .getPetById(String.valueOf(idFromService) )
                .then()
                .log().body()
                .statusCode(200);

//        SoftAssertions assertions = new SoftAssertions();
////        assertions.assertThat(petFromService.getName()).as("Name").isEqualTo(murchyk.getName());
//        assertions.assertThat(petFromService.getName()).as("Name").isEqualTo("Murchyk2");
////        assertions.assertThat(petFromService.getStatus()).as("Status").isEqualTo(murchyk.getStatus());
//        assertions.assertThat(petFromService.getStatus()).as("Status").isEqualTo("available");
//        assertions.assertAll();

    }

    @BeforeClass
    public static void cleanUp() {
        List<Pet> petList = new PetStorePetEndPoint()
                .getPetByStatus("available")
                .body()
                .jsonPath().getList("$", Pet.class);

        List<Pet> petList2 = new PetStorePetEndPoint()
                .getPetByStatus("available")
                .body()
                .jsonPath().getList("findAll {item -> item.name == 'Murchyk2' }", Pet.class);

        List<Pet> petList3 = new PetStorePetEndPoint()
                .getPetByStatus("available")
                .body()
                .jsonPath().getList("findAll {item -> item.name == 'Murchyk3' }", Pet.class);

        for(Pet pet : petList2) {
            new PetStorePetEndPoint().deleteById(pet.getId());
        }

        System.out.println();
    }
}
