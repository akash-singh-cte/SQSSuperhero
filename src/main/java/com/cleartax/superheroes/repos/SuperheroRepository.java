package com.cleartax.superheroes.repos;

import com.cleartax.superheroes.entities.Superhero;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SuperheroRepository extends MongoRepository<Superhero, String> {
}
