import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import models.User;

public class PetStoreUserEndPoint {

    public Response getUserName(String name) {
        return given()
                .pathParam("username", name)
                .when()
                .get(Config.USER_NAME)
                .then().extract().response();
    }


    public Response createUser(User user) {
        return given()
                .body(user)
                .when()
                .post(Config.CREATE_USER)
                .then().extract().response();
    }

    public Response deleteByName(String name) {
        return given()
                .when()
                .delete(Config.USER_NAME, name)
                .then().extract().response();
    }
    public Response updatePet(User user, String name) {
        return given()
                .pathParam("username", name)
                .body(user)
                .when()
                .put(Config.USER_NAME)
                .then().extract().response();
    }

    private RequestSpecification given() {
        return RestAssured.given()
                .log().uri()
                .log().body()
                .baseUri(Config.PETSTORE_BASE_URL)
                .contentType(ContentType.JSON);
    }
}
