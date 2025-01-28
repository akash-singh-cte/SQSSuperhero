package com.cleartax.superheroes.services;

import com.cleartax.superheroes.config.SqsConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

@Service
public class SuperheroConsumer {
  @Autowired
  private SqsConfig sqsConfig;
  @Autowired
  private SqsClient sqsClient;

  @Scheduled(fixedDelay = 10000)
  public void consumeSuperhero() {
    try {
      ReceiveMessageResponse receiveMessageResponse = sqsClient.receiveMessage(ReceiveMessageRequest.builder()
              .queueUrl(sqsConfig.getQueueUrl())
              .maxNumberOfMessages(1)  // Adjust according to your needs
              .waitTimeSeconds(10) // Enable long polling
              .build());

      receiveMessageResponse.messages().forEach(message -> {

        String body = message.body();
        System.out.println("Queue Message: " + body);


        sqsClient.deleteMessage(DeleteMessageRequest.builder()
                .queueUrl(sqsConfig.getQueueUrl())
                .receiptHandle(message.receiptHandle())
                .build());
      });
    } catch (SqsException e) {
      System.err.println("Error: " + e.awsErrorDetails().errorMessage());
      System.out.println("Empty Queue Error");
    }

  }
}