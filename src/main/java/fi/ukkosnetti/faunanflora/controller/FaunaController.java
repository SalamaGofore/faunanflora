package fi.ukkosnetti.faunanflora.controller;

import fi.ukkosnetti.faunanflora.db.models.Fauna;
import fi.ukkosnetti.faunanflora.db.repository.FaunaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import java.util.List;
import java.util.Optional;

@RestController
public class FaunaController {

    private final FaunaRepository repo;

    public FaunaController(FaunaRepository repo) {
        this.repo = repo;
    }

    @GetMapping(path = "/api/fauna",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Fauna> getAll() {
        return (List<Fauna>) repo.findAll();
    }

    @PostMapping(path = "/api/fauna",
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Fauna> postFauna(@RequestBody String fauna) {
        return new ResponseEntity<>(repo.save(new Fauna(fauna)), HttpStatus.CREATED);
    }

    @GetMapping(path = "/api/fauna/{name}",
      produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Fauna> getByName(@PathVariable String name) {
        Optional<Fauna> fauna = repo.findByName(name);
        return fauna.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
          .orElseGet(() -> new ResponseEntity<>(null, HttpStatus.NOT_FOUND));
    }
}
