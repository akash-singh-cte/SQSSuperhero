package com.cleartax.superheroes.repos;

import com.cleartax.superheroes.entities.Superhero;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

public interface SuperheroRepository extends MongoRepository<Superhero, String> {
    Optional<Superhero> findByName(String name);
}
