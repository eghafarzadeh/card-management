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
public class PaymentNotificationRetryConsumerService implements ConsumerService {

    private final PaymentNotificationClient paymentNotificationClient;
    private final RetryTopicProducerService retryTopicProducerService;

    public PaymentNotificationRetryConsumerService(PaymentNotificationClient paymentNotificationClient, RetryTopicProducerService retryTopicProducerService) {
        this.paymentNotificationClient = paymentNotificationClient;
        this.retryTopicProducerService = retryTopicProducerService;
    }

    @Override
    @KafkaListener(topics = RetryTopicProducerService.TOPIC, groupId = "payment-notifications")
    public void consume(String message) {
        try {
            paymentNotificationClient.processMessage(message);
        } catch (RestClientException e) {
            try {
                Thread.sleep(300000); // 5 min
            } catch (InterruptedException ex) {
                log.info(ex.getMessage());
            }
            publishToRetryTopic(message);
        }
        log.info(String.format("$$$$ => Consumed message: %s", message));
    }

    public void publishToRetryTopic(String message) {
        retryTopicProducerService.sendMessage(message);
    }
}
