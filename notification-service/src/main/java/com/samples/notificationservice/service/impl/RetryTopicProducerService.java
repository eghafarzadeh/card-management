package com.samples.notificationservice.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Elham
 * @since 10/1/2020
 */
@Service
@Slf4j
public class RetryTopicProducerService {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final String TOPIC = "paymentNotificationsRetryTopic";

    public RetryTopicProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendMessage(String message) {
        log.info(String.format("$$$$ => Producing message: %s", message));
        kafkaTemplate.send(TOPIC, message);
    }
}
