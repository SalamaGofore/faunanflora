package fi.ukkosnetti.faunanflora.db.repository;

import fi.ukkosnetti.faunanflora.db.models.Fauna;
import org.springframework.data.repository.CrudRepository;

public interface FaunaRepository extends CrudRepository<Fauna, Long> {
}
