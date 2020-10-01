package com.samples.notificationservice.service;

import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

/**
 * @author Elham
 * @since 10/1/2020
 */
@Service
public class ConsumerService {

    @KafkaListener(topics = "kafkaTopic", groupId = "payment-notifications")
    public void consume(String message) {
        System.out.println(String.format("$$$$ => Consumed message: %s", message));
    }
}
