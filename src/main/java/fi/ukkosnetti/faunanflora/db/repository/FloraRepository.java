package fi.ukkosnetti.faunanflora.db.repository;

import fi.ukkosnetti.faunanflora.db.models.Flora;
import org.springframework.data.repository.CrudRepository;

public interface FloraRepository extends CrudRepository<Flora, Long> {

}
