import io.restassured.response.Response;
import models.User;
import org.assertj.core.api.Assertions;
import org.assertj.core.api.SoftAssertions;
import org.junit.BeforeClass;
import org.junit.Test;

import java.util.List;

import static io.restassured.RestAssured.given;


public class CrudTests {
    @BeforeClass
    public static void cleanUp() {
            new PetStoreUserEndPoint().deleteByName("Vasya");
    }
    @Test
    public void createUser() {
        User user = User.createUserPet(1, "Vasya");
        new PetStoreUserEndPoint().createUser(user);
        Response userResponse = new PetStoreUserEndPoint().getUserName(user.getUsername());
        User userFromService = userResponse.body().as(User.class);
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(userFromService.getUsername()).as("Name").isEqualTo(user.getUsername());
        assertions.assertThat(userFromService.getId()).as("id").isEqualTo(user.getId());
        assertions.assertAll();


    }

    @Test
    public void  readUser() {
        User user = User.createUserPet(1, "Vasya");
        new PetStoreUserEndPoint().createUser(user);
        Response userResponse = new PetStoreUserEndPoint().getUserName(user.getUsername());
        User userFromService = userResponse.body().as(User.class);
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(userFromService.getUsername()).as("Name").isEqualTo(user.getUsername());
        assertions.assertThat(userFromService.getId()).as("id").isEqualTo(user.getId());
        assertions.assertAll();

    }
    @Test
    public void updateUser() {
        User user = User.createUserPet(1, "Vasya");
        new PetStoreUserEndPoint().createUser(user);

        user.setUsername("Petro");

        new PetStoreUserEndPoint().updatePet(user, "Vasya");
        Response userResponse = new PetStoreUserEndPoint().getUserName(user.getUsername());
        User userFromService = userResponse.body().as(User.class);
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(userFromService.getUsername()).as("Name").isEqualTo(user.getUsername());
        assertions.assertThat(userFromService.getId()).as("id").isEqualTo(user.getId());
        assertions.assertAll();

    }
    @Test
    public void userDelete() {
        User user = User.createUserPet(1, "Vasya");
        new PetStoreUserEndPoint().createUser(user);

        Response userResponse = new PetStoreUserEndPoint().getUserName(user.getUsername());
        User userFromService = userResponse.body().as(User.class);
        new PetStoreUserEndPoint().deleteByName(userFromService.getUsername());
        Response afterDeleteUserResponse = new PetStoreUserEndPoint().getUserName(user.getUsername());
        Assertions.assertThat(userResponse.statusCode()).isEqualTo(200);
        Assertions.assertThat(afterDeleteUserResponse.statusCode()).isEqualTo(404);


    }
}