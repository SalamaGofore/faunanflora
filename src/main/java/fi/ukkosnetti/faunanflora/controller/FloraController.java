package fi.ukkosnetti.faunanflora.controller;

import fi.ukkosnetti.faunanflora.db.models.Fauna;
import fi.ukkosnetti.faunanflora.db.models.Flora;
import fi.ukkosnetti.faunanflora.db.repository.FaunaRepository;
import fi.ukkosnetti.faunanflora.db.repository.FloraRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class FloraController {

    private final FloraRepository repo;

    public FloraController(FloraRepository repo) {
        this.repo = repo;
    }

    @GetMapping(path = "/api/flora",
            produces = MediaType.APPLICATION_JSON_VALUE)
    public List<Flora> getAll() {
        return (List<Flora>) repo.findAll();
    }

    @PostMapping(path = "/api/flora",
            consumes = MediaType.TEXT_PLAIN_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Flora> postFlora(@RequestBody String flora) {
        return new ResponseEntity<>(repo.save(new Flora(flora)), HttpStatus.CREATED);
    }
}
