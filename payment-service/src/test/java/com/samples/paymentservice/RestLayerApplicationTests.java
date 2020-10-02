package com.samples.paymentservice;

import com.samples.paymentservice.rest.controller.CardController;
import com.samples.paymentservice.rest.controller.TransactionController;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * @author Elham
 * @since 10/2/2020
 */
@SpringBootTest
public class RestLayerApplicationTests {

    private final CardController cardController;
    private final TransactionController transactionController;

    @Autowired
    public RestLayerApplicationTests(CardController cardController, TransactionController transactionController) {
        this.cardController = cardController;
        this.transactionController = transactionController;
    }

    @Test
    void contextLoads() {
        assertThat(cardController).isNotNull();
        assertThat(transactionController).isNotNull();
    }
}
