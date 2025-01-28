package com.cleartax.superheroes.entities;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document(collection = "superheroes")  // MongoDB collection name
public class Superhero {

    @Id
    private String id;
    private String name;
    private String power;
    private String universe;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    // Getter and Setter for `name`
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    // Getter and Setter for `power`
    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

    // Getter and Setter for `universe`
    public String getUniverse() {
        return universe;
    }

    public void setUniverse(String universe) {
        this.universe = universe;
    }
}
