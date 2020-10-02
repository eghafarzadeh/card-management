package com.samples.paymentservice;

import com.samples.paymentservice.service.impl.Payment1Client;
import com.samples.paymentservice.service.impl.Payment2Client;
import com.samples.paymentservice.service.inf.CardService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Elham
 * @since 10/2/2020
 */
@SpringBootTest
public class ServiceLayerApplicationTests {

    private final CardService cardService;
    private final Payment1Client payment1Client;
    private final Payment2Client payment2Client;

    @Autowired
    public ServiceLayerApplicationTests(CardService cardService, Payment1Client payment1Client, Payment2Client payment2Client) {
        this.cardService = cardService;
        this.payment1Client = payment1Client;
        this.payment2Client = payment2Client;
    }

    @Test
    void contextLoads() {
        assertThat(cardService).isNotNull();
        assertThat(payment1Client).isNotNull();
        assertThat(payment2Client).isNotNull();
    }
}
