package fi.ukkosnetti.faunanflora.db.repository;

import fi.ukkosnetti.faunanflora.db.models.Fauna;
import org.springframework.data.jdbc.repository.query.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface FaunaRepository extends CrudRepository<Fauna, Long> {

  @Query("SELECT * FROM Fauna f where f.name = :name")
  public Optional<Fauna> findByName(@Param("name") String name);

  @Query("SELECT f.* FROM Fauna f JOIN Habitat h on h.id = f.habitat WHERE h.name = :habitat ")
  public List<Fauna> findAllByHabitat(@Param("habitat") String habitat);

}
