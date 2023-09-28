package fi.ukkosnetti.faunanflora.controller;

import fi.ukkosnetti.faunanflora.AbstractTest;
import fi.ukkosnetti.faunanflora.db.models.Fauna;
import fi.ukkosnetti.faunanflora.db.models.Habitat;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

class HabitatControllerTest extends AbstractTest {

  @Test
  void getsAnimalsInHabitat() {
    initData();

    given()
      .contentType(ContentType.JSON)
      .when()
      .get("/api/habitat/Safari/fauna")
      .then()
      .statusCode(200)
      .body(".", hasSize(2));
  }

  @Test
  void getsHabitatWithAllData() {
    initData();

    given()
      .contentType(ContentType.JSON)
      .when()
      .get("/api/habitat/Safari")
      .then()
      .statusCode(200)
      .body("name", equalTo("Safari"))
      .body("fauna", hasSize(2));
  }

  @Test
  void getsHabitat() {
    initData();

    given()
      .contentType(ContentType.JSON)
      .when()
      .get("/api/habitat/Safari/lite")
      .then()
      .statusCode(200)
      .body("name", equalTo("Safari"));
  }

  private void initData() {
    Habitat habitat = new Habitat("Safari");
    habitat.addFauna(new Fauna("Lion"));
    habitat.addFauna(new Fauna("Zebra"));
    habitatRepository.save(habitat);

    faunaRepository.save(new Fauna("Tiger"));
  }

}
