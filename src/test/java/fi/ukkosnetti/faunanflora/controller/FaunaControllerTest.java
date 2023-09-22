package fi.ukkosnetti.faunanflora.controller;

import fi.ukkosnetti.faunanflora.AbstractTest;
import fi.ukkosnetti.faunanflora.db.models.Fauna;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

class FaunaControllerTest extends AbstractTest {

    @Test
    void getsAnimals() {
        List<Fauna> animals = List.of(
                new Fauna("Deer"),
                new Fauna("Moose")
        );
        faunaRepository.saveAll(animals);

        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/fauna")
                .then()
                .statusCode(200)
                .body(".", hasSize(2));
    }

    @Test
    void savesAnimal() {
        given()
                .contentType(ContentType.TEXT)
                .body("Reindeer")
                .post("/api/fauna")
                .then()
                .statusCode(201);
        given()
                .contentType(ContentType.JSON)
                .when()
                .get("/api/fauna")
                .then()
                .statusCode(200)
                .body(".", hasSize(1));
    }

    @Test
    void findsByName() {
        List<Fauna> animals = List.of(
          new Fauna("Crocodile"),
          new Fauna("Alligator")
        );
        faunaRepository.saveAll(animals);
        given()
          .contentType(ContentType.JSON)
          .when()
          .get("/api/fauna/Crocodile")
          .then()
          .statusCode(200)
          .body("name", equalTo("Crocodile"))
          .body("id", equalTo(1));
        given()
          .contentType(ContentType.JSON)
          .when()
          .get("/api/fauna/Alligator")
          .then()
          .statusCode(200)
          .body("name", equalTo("Alligator"))
          .body("id", equalTo(2));
        given()
          .contentType(ContentType.JSON)
          .when()
          .get("/api/fauna/Turtle")
          .then()
          .statusCode(404);
    }
}
