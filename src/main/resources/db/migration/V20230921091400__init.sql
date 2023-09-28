CREATE TABLE Habitat (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE
);

CREATE TABLE Flora (
    id SERIAL PRIMARY KEY ,
    name VARCHAR(255) UNIQUE
);

CREATE TABLE Fauna (
    id SERIAL PRIMARY KEY,
    name VARCHAR(255) UNIQUE,
    habitat int,
    CONSTRAINT fk_fauna_habitat
        FOREIGN KEY(habitat)
                   REFERENCES Habitat(id)
                   ON DELETE CASCADE
);