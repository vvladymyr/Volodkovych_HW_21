import io.restassured.RestAssured;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.User;
import org.assertj.core.api.SoftAssertions;
import org.hamcrest.Matchers;
import org.junit.Test;

import static io.restassured.RestAssured.given;


public class UserTestsPetStore {
    @Test
    public void createUserVerifyCode() {
        User user = new User();
        user.setId(1);
        user.setUsername("Vasya");
        user.setFirstName("Vasyl");
        user.setLastName("Tymchenko");
        user.setEmail("vasya@ukr.net");
        user.setPassword("vasya");
        user.setPhone("5555555");
        user.setUserStatus(0);
        new PetStoreUserEndPoint().createUser(user)
                .then()
                .statusCode(200);

    }

    @Test
    public void userIdVerify() {
        User user = new User();
        user.setId(1);
        user.setUsername("Vasya");
        user.setFirstName("Vasyl");
        user.setLastName("Tymchenko");
        user.setEmail("vasya@ukr.net");
        user.setPassword("vasya");
        user.setPhone("5555555");
        user.setUserStatus(0);
        new PetStoreUserEndPoint().createUser(user);
        Response responseUser = new PetStoreUserEndPoint().getUserName(user.getUsername());
        User userFromService = responseUser.body().as(User.class);
        SoftAssertions assertions = new SoftAssertions();
        assertions.assertThat(userFromService.getId()).isNotNull();
        assertions.assertAll();

    }

    @Test
    public void userDeleteVerify() {
        User user = new User();
        user.setId(1);
        user.setUsername("Vasya");
        user.setFirstName("Vasyl");
        user.setLastName("Tymchenko");
        user.setEmail("vasya@ukr.net");
        user.setPassword("vasya");
        user.setPhone("5555555");
        user.setUserStatus(0);
        new PetStoreUserEndPoint().createUser(user);
        new PetStoreUserEndPoint().deleteByName(user.getUsername())
                .then()
                .statusCode(200);
        new PetStoreUserEndPoint().getUserName(user.getUsername())
                .then()
                .statusCode(404);

    }
}