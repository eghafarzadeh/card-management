package com.samples.notificationservice.service.impl;

import com.samples.notificationservice.service.inf.ConsumerService;
import com.samples.notificationservice.service.inf.PaymentNotificationClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClientException;

/**
 * @author Elham
 * @since 10/1/2020
 */
@Service
@Slf4j
public class PaymentNotificationConsumerService implements ConsumerService {

    private final PaymentNotificationClient paymentNotificationClient;
    private final RetryTopicProducerService retryTopicProducerService;

    public PaymentNotificationConsumerService(PaymentNotificationClient paymentNotificationClient, RetryTopicProducerService retryTopicProducerService) {
        this.paymentNotificationClient = paymentNotificationClient;
        this.retryTopicProducerService = retryTopicProducerService;
    }

    @Override
    @KafkaListener(topics = "paymentNotificationsTopic", groupId = "payment-notifications")
    public void consume(String message) {
        try {
            paymentNotificationClient.processMessage(message);
        } catch (RestClientException e) {
            publishToRetryTopic(message);
        }
        log.info(String.format("$$$$ => Consumed message: %s", message));
    }

    public void publishToRetryTopic(String message) {
        retryTopicProducerService.sendMessage(message);
    }
}
