package com.samples.notificationservice.service.impl;

import com.samples.notificationservice.service.inf.ProducerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Elham
 * @since 10/1/2020
 */
@Service
@Slf4j
public class RetryTopicProducerService implements ProducerService {

    public static final String TOPIC = "paymentNotificationsRetryTopic";
    private final KafkaTemplate<String, String> kafkaTemplate;

    public RetryTopicProducerService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    @Override
    public void sendMessage(String message) {
        log.info(String.format("$$$$ => Producing message: %s", message));
        kafkaTemplate.send(TOPIC, message);
    }
}
