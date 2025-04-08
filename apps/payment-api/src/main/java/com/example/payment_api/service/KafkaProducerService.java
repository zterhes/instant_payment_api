package com.example.payment_api.service;

import com.example.shared_lib.dto.KafkaMessage;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;

@Log4j2
@Service
public class KafkaProducerService {
    private final String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public KafkaProducerService(KafkaTemplate<String, String> kafkaTemplate,
                                @Value("${kafka.topic}") String topic) {
        this.topic = topic;
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String messageContent) {
        var messageId = UUID.randomUUID().toString();
        var message = new KafkaMessage(messageId, messageContent, Instant.now());

        log.info("Sending message to topic {}: {}", topic, message);

       kafkaTemplate.send(topic, message.toString()).whenComplete((result, ex) -> {
            if (ex != null) {
                log.error("Error sending message to topic {}: {}", topic, message, ex);
            } else {
                log.info("Message sent to topic {}: {}", topic, message);
            }
        });

    }
}