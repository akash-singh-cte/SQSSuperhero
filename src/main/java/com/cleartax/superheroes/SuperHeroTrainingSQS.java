package com.cleartax.superheroes;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling  // Enable scheduling
public class SuperHeroTrainingSQS {

	public static void main(String[] args) {
		SpringApplication.run(SuperHeroTrainingSQS.class, args);
	}

}
