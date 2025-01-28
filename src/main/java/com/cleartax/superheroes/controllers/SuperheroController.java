package com.cleartax.superheroes.controllers;

import com.cleartax.superheroes.config.SqsConfig;
import com.cleartax.superheroes.entities.Superhero;
import com.cleartax.superheroes.dto.SuperheroRequestBody;
import com.cleartax.superheroes.services.QueueService;
import com.cleartax.superheroes.services.SuperheroService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.SendMessageRequest;
import software.amazon.awssdk.services.sqs.model.SendMessageResponse;

import java.util.List;

@RestController
public class SuperheroController {

    private SuperheroService superheroService;

    @Autowired
    private SqsConfig sqsConfig;

    @Autowired
    private SqsClient sqsClient;

    @Autowired
    private QueueService queueService;

    @Autowired
    public SuperheroController(SuperheroService superheroService, SqsClient sqsClient){
        this.superheroService = superheroService;
        this.sqsClient = sqsClient;
    }

    @GetMapping("/get_messages")
    public List<String> getAllMessages() {
        return queueService.getAllMessagesInQueue();
    }

    @PostMapping("/push_superhero_to_queue/{super_hero_name}")
    public String pushSuperhero(@PathVariable String super_hero_name) {
        superheroService.pushSuperheroToQueue(super_hero_name, sqsConfig.getQueueUrl());
        return "The superhero has been pushed to the queue.";
    }

    @PostMapping("/push_superheroes_to_queue")
    public String pushAllSuperheroes() {
        superheroService.pushAllSuperheroesToQueue(sqsConfig.getQueueUrl());
        return "All superheroes have been pushed to the queue.";
    }

    @GetMapping("/hello")
    public String hello(@RequestParam(value = "username", defaultValue = "World") String username) {
        sqsClient.sendMessage(SendMessageRequest.builder()
            .queueUrl(sqsConfig.getQueueUrl())
            .messageBody("SuperMan")
            .build());

        return String.format("Hello %s!, %s", username);
    }

    @PostMapping("/superhero")
    public Superhero persistSuperhero(@RequestBody SuperheroRequestBody superheroRequestBody){
        return superheroService.persistSuperhero(superheroRequestBody, superheroRequestBody.getUniverse());
    }

    @GetMapping("/async_update")
    public String updateSuperhero(@RequestParam(value = "super_hero_name", defaultValue = "spiderMan") String superHeroName) {
        SendMessageResponse result = sqsClient.sendMessage(SendMessageRequest.builder()
            .queueUrl(sqsConfig.getQueueUrl())
            .messageBody(superHeroName)
            .build());

        return String.format("Message id %s and superHero %s", result.messageId(), superHeroName);
    }


}
