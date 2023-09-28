package fi.ukkosnetti.faunanflora.db.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;

public class Fauna {

    @Id
    public Long id;

    public String name;

    @Transient
    public Habitat habitat;

    public Fauna() {

    }

    public Fauna(String name) {
        this.name = name;
    }

    public Fauna(String name, Habitat habitat) {
        this.name = name;
        this.habitat = habitat;
    }
}
