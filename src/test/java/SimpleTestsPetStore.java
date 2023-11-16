import io.restassured.response.Response;
import org.hamcrest.Matchers;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runners.MethodSorters;

import static io.restassured.RestAssured.given;

@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class SimpleTestsPetStore {

    @Test
    public void testMethod() {
        given()
                .baseUri("https://petstore.swagger.io/v2")
                .when()
                .get("/pet/findByStatus?status=available")
                .then()
                .statusCode(200);
    }

    @Test
    public void newTestMethod() {
        given()
                .baseUri("https://petstore.swagger.io/v2")
//                .log().uri()
                .when()
                .get("/pet/findByStatus?status=available")
                .then()
                .statusCode(200);
    }

    @Test
    public void testMethodStatusCode() {
        Response response = given()
                .log().uri()
                .baseUri("https://petstore.swagger.io/v2")
                .when()
                .get("/pet/findByStatus?status=available")
                .then()
                .extract().response();

        response.then().statusCode(200);
    }

    @Test
    public void testMethodVerifyBody() {
         given()
                .log().uri()
                .baseUri("https://petstore.swagger.io/v2")
                .when()
                .get("/pet/findByStatus?status=available")
                .then()
                .assertThat().body(Matchers.notNullValue());
    }

    @Test
    public void bverifyStatusCode() {
        new PetStorePetEndPoint()
                .getPetByStatus("available")
                .then()
                .log().status()
                .statusCode(200);
    }

    @Test
    public void averifyBodyUseParam() {
        new PetStorePetEndPoint()
                .getPetByStatus("available")
                .then()
                .assertThat()
                .log().status()
                .body(Matchers.notNullValue());
    }

    @Test
    public void cverifyNotExistingPet() {
        new PetStorePetEndPoint()
                .getPetById("0")
                .then()
                .log().body()
                .statusCode(404);
    }

    @Test
    public void dverifyNotExistingPetReturn404() {
        new PetStorePetEndPoint()
                .getPetById("123456789012345")
                .then()
                .log().body()
                .statusCode(404);
    }

}
