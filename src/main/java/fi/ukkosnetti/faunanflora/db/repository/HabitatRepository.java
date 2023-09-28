package fi.ukkosnetti.faunanflora.db.repository;

import fi.ukkosnetti.faunanflora.db.models.Habitat;
import fi.ukkosnetti.faunanflora.db.models.HabitatLite;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface HabitatRepository extends CrudRepository<Habitat, Long> {

  @Query("SELECT h.id as id, h.name as name, f.* as fauna FROM Habitat h JOIN Fauna f ON f.habitat = h.id WHERE h.name = :habitat")
  Optional<Habitat> getHabitatFull(@Param("habitat") String habitat);

  Optional<Habitat> findByName(String name);

  @Query("SELECT h.id as id, h.name as name FROM Habitat h WHERE h.name = :habitat")
  Optional<HabitatLite> findByNameLite(@Param("habitat") String name);

}
