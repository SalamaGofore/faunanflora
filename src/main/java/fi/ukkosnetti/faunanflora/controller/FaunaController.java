package fi.ukkosnetti.faunanflora.controller;

import fi.ukkosnetti.faunanflora.db.models.Fauna;
import fi.ukkosnetti.faunanflora.db.repository.FaunaRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

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
}
