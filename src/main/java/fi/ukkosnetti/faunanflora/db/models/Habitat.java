package fi.ukkosnetti.faunanflora.db.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.PersistenceConstructor;
import org.springframework.data.annotation.PersistenceCreator;
import org.springframework.data.relational.core.mapping.Table;
;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class Habitat {

  @Id
  public Long id;

  public String name;

  public final Set<Fauna> fauna = new HashSet<>();

  public Habitat(String name) {
    this.name = name;
  }

  @PersistenceCreator
  public Habitat(String name, Collection<Fauna> fauna) {
    this.name = name;
    this.fauna.addAll(fauna);
  }

  public void addFauna(Fauna fauna) {
    this.fauna.add(fauna);
    fauna.habitat = this;
  }
}
