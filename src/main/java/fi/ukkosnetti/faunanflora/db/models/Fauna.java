package fi.ukkosnetti.faunanflora.db.models;

import org.springframework.data.annotation.Id;

public class Fauna {

    @Id
    public Long id;

    public String name;

    public Fauna(String name) {
        this.name = name;
    }
}
