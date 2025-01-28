package com.cleartax.superheroes.services;

import com.cleartax.superheroes.entities.Superhero;
import com.cleartax.superheroes.dto.SuperheroRequestBody;
import com.cleartax.superheroes.repos.SuperheroRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;

import java.util.List;

@Service
public class SuperheroService {

    private SuperheroRepository superheroRepository;
    private SqsClient sqsClient;

    @Autowired
    public SuperheroService(SuperheroRepository superheroRepository, SqsClient sqsClient) {
        this.superheroRepository = superheroRepository;
        this.sqsClient = sqsClient;
    }

    public Superhero persistSuperhero(SuperheroRequestBody requestBody, String universe){

        Superhero superhero = new Superhero();
        superhero.setName(requestBody.getSuperheroName().toUpperCase());
        superhero.setPower(requestBody.getPower());
        if(isDC(universe)){
            superhero.setUniverse("Bahubali");
        } else {
            superhero.setUniverse("Marvel");
        }
        Superhero superhero1 =  superheroRepository.save(superhero);
        return superhero1;
    }

    private boolean isDC(String universe){
        if(universe.equals("DC")){
            return true;
        } else {
            return false;
        }
    }

    public void pushSuperheroToQueue(String superHeroName, String queueUrl) {
            String messageBody = superHeroName;
            SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(messageBody)
                    .build();
            sqsClient.sendMessage(sendMessageRequest);
    }

    public void pushAllSuperheroesToQueue(String queueUrl) {
        List<Superhero> superheroes = superheroRepository.findAll();
        for (Superhero superhero : superheroes) {
            String messageBody = superhero.getName() + "," + superhero.getPower() + "," + superhero.getUniverse();
            SendMessageRequest sendMessageRequest = SendMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .messageBody(messageBody)
                    .build();
            sqsClient.sendMessage(sendMessageRequest);
        }
    }
}
