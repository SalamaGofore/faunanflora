package fi.ukkosnetti.faunanflora.db.models;

import org.springframework.data.annotation.Id;

public class Flora {

    @Id
    public Long id;

    public String name;

    public Flora(String name) {
        this.name = name;
    }
}
