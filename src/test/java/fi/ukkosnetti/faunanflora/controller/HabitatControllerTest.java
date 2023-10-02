package fi.ukkosnetti.faunanflora.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import fi.ukkosnetti.faunanflora.AbstractTest;
import fi.ukkosnetti.faunanflora.db.models.Fauna;
import fi.ukkosnetti.faunanflora.db.models.Habitat;
import fi.ukkosnetti.faunanflora.db.models.Metadata;
import io.restassured.http.ContentType;
import net.minidev.json.JSONObject;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

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
  void getsHabitatWithAllData() throws JsonProcessingException {
    initData();

    given()
      .contentType(ContentType.JSON)
      .when()
      .get("/api/habitat/Safari")
      .then()
      .statusCode(200)
      .body("name", equalTo("Safari"))
      .body("metadata", notNullValue())
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

    habitat.metadata = createMetadata();
    habitatRepository.save(habitat);

    faunaRepository.save(new Fauna("Tiger"));
  }

  private Metadata createMetadata() {
    Metadata md = new Metadata();
    md.metadata.add(new Metadata.MetadataField("MinTemp", "15 celcius"));
    md.metadata.add(new Metadata.MetadataField("MaxTemp", "35 celcius"));
    return md;
  }

}
