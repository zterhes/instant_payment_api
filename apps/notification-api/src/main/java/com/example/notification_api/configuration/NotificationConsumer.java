package com.example.notification_api.configuration;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Component
public class NotificationConsumer {

    @KafkaListener(topics = "notification", groupId = "notification-group")
    public void listen(String message) {
        System.out.println("==========================");
        System.out.println("Received notification: " + message);
        System.out.println("==========================");
    }
}
