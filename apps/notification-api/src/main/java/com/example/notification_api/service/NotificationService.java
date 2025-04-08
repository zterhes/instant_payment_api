package com.example.notification_api.service;

import com.example.notification_api.service.client.PaymentApiClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationService {

    @Autowired
    private PaymentApiClient paymentApiClient;

    public void sendNotification( String transactionId) {

       var transaction = paymentApiClient.getTransaction(transactionId);
       // Here we can send notification

    }
}
