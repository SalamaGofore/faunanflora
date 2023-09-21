package fi.ukkosnetti.faunanflora.controller;

import fi.ukkosnetti.faunanflora.AbstractTest;
import fi.ukkosnetti.faunanflora.db.models.Fauna;
import fi.ukkosnetti.faunanflora.db.repository.FaunaRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static io.restassured.RestAssured.given;
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
}
