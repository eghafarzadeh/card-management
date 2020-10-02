package com.samples.notificationservice;

import com.samples.notificationservice.service.impl.PaymentNotificationConsumerService;
import com.samples.notificationservice.service.impl.PaymentNotificationRetryConsumerService;
import com.samples.notificationservice.service.inf.PaymentNotificationClient;
import com.samples.notificationservice.service.inf.ProducerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class NotificationServiceApplicationTests {

    private final PaymentNotificationClient paymentNotificationClient;
    private final ProducerService producerService;
    private final PaymentNotificationConsumerService paymentNotificationConsumerService;
    private final PaymentNotificationRetryConsumerService paymentNotificationRetryConsumerService;

    @Autowired
    NotificationServiceApplicationTests(PaymentNotificationClient paymentNotificationClient, ProducerService producerService, PaymentNotificationConsumerService paymentNotificationConsumerService, PaymentNotificationRetryConsumerService paymentNotificationRetryConsumerService) {
        this.paymentNotificationClient = paymentNotificationClient;
        this.producerService = producerService;
        this.paymentNotificationConsumerService = paymentNotificationConsumerService;
        this.paymentNotificationRetryConsumerService = paymentNotificationRetryConsumerService;
    }

    @Test
    void contextLoads() {
        assertThat(paymentNotificationClient).isNotNull();
        assertThat(producerService).isNotNull();
        assertThat(paymentNotificationConsumerService).isNotNull();
        assertThat(paymentNotificationRetryConsumerService).isNotNull();
    }

}
