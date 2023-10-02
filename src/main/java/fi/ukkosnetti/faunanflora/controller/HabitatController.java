package fi.ukkosnetti.faunanflora.controller;

import fi.ukkosnetti.faunanflora.db.models.Fauna;
import fi.ukkosnetti.faunanflora.db.models.Habitat;
import fi.ukkosnetti.faunanflora.db.models.HabitatLite;
import fi.ukkosnetti.faunanflora.db.repository.FaunaRepository;
import fi.ukkosnetti.faunanflora.db.repository.HabitatRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
public class HabitatController {

  private final HabitatRepository repo;

  private final FaunaRepository faunaRepo;

  public HabitatController(FaunaRepository faunaRepo, HabitatRepository repo) {
    this.repo = repo;
    this.faunaRepo = faunaRepo;
  }

  @GetMapping(path = "/api/habitat/{habitat}")
  public ResponseEntity<Habitat> getFullHabitat(@PathVariable String habitat) {
    Optional<Habitat> habit = repo.findByName(habitat);
    return habit.map(h -> new ResponseEntity<>(h, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @GetMapping(path = "/api/habitat/{habitat}/lite")
  public ResponseEntity<HabitatLite> getLiteHabitat(@PathVariable String habitat) {
    Optional<HabitatLite> habit = repo.findHabitatLiteByName(habitat);
    return habit.map(h -> new ResponseEntity<>(h, HttpStatus.OK)).orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
  }

  @GetMapping(path = "/api/habitat/{habitat}/fauna",
    produces = MediaType.APPLICATION_JSON_VALUE)
  public List<Fauna> getAllFaunaByHabitat(@PathVariable String habitat) {
    return faunaRepo.findAllByHabitat(habitat);
  }
}
