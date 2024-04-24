package reqres.tests;

import io.qameta.allure.Feature;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import reqres.models.RegisterBodyModel;
import reqres.models.RegisterResponseModel;
import reqres.models.SingleUserModel;

import static io.qameta.allure.Allure.step;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.is;
import static reqres.specs.RestApiSpecs.requestSpec;
import static reqres.specs.RestApiSpecs.responseSpec;

public class RestApiTests extends BaseTest {

    @Test
    @Feature("POST /api/register")
    @DisplayName("Successful registration")
    public void successfulRegisterTest() {
        RegisterBodyModel registerBody = new RegisterBodyModel();
        registerBody.setEmail("eve.holt@reqres.in");
        registerBody.setPassword("pistol");

        RegisterResponseModel response = step("Make request", ()->
                given(requestSpec)
                    .body(registerBody)
                    .when()
                    .post("/register")
                    .then()
                    .spec(responseSpec)
                    .statusCode(200)
                    .extract().as(RegisterResponseModel.class));

        step("Verify response", ()-> {
            Assertions.assertEquals(4, response.getId());
            Assertions.assertEquals("QpwL5tke4Pnpja7X4", response.getToken());
        });
    }

    @Test
    @Feature("POST /api/register")
    @DisplayName("Unsuccessful registration - missing password")
    public void unSuccessfulRegisterTest() {
        RegisterBodyModel registerBody = new RegisterBodyModel();
        registerBody.setEmail("eve.holt@reqres.in");
        registerBody.setPassword("");

        step("Check 400 error when password is missing", ()->
                given(requestSpec)
                        .body(registerBody)
                        .when()
                        .post("/register")
                        .then()
                        .spec(responseSpec)
                        .statusCode(400)
                        .body("error", is("Missing password")));
    }

    @Test
    @Feature("GET /api/users/")
    @DisplayName("Successful getting single user")
    public void successfulGettingSingleUserTest() {

        SingleUserModel response = step("Make request", ()->
                given(requestSpec)
                        .when()
                        .get("/users/2")
                        .then()
                        .spec(responseSpec)
                        .statusCode(200)
                        .extract().as(SingleUserModel.class));

        step("Verify response data{}", ()-> {
            Assertions.assertEquals(2, response.getData().getId());
            Assertions.assertEquals("janet.weaver@reqres.in", response.getData().getEmail());
            Assertions.assertEquals("Janet", response.getData().getFirstName());
            Assertions.assertEquals("Weaver", response.getData().getLastName());
            Assertions.assertEquals("https://reqres.in/img/faces/2-image.jpg", response.getData().getAvatar());
        });
    }

}
