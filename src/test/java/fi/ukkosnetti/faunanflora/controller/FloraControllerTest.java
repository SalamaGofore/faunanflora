package fi.ukkosnetti.faunanflora.controller;

import fi.ukkosnetti.faunanflora.AbstractTest;
import fi.ukkosnetti.faunanflora.db.models.Fauna;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;

class FloraControllerTest extends AbstractTest {

    @Test
    void savesFlower() {
        given()
                .contentType(ContentType.TEXT)
                .body("Sunflower")
                .post("/api/flora")
                .then()
                .statusCode(201);
    }

    @Test
    void getsFlowers() {
        given()
                .contentType(ContentType.TEXT)
                .body("Sunflower")
                .post("/api/flora")
                .then()
                .statusCode(201);
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/flora")
                .then()
                .statusCode(200)
                .body(".", hasSize(1));
    }

}
