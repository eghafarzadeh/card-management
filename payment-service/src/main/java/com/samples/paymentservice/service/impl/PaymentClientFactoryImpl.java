package com.samples.paymentservice.service.impl;

import com.samples.paymentservice.service.inf.PaymentClient;
import com.samples.paymentservice.service.inf.PaymentClientFactory;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Elham
 * @since 10/1/2020
 */
@Component
public class PaymentClientFactoryImpl implements PaymentClientFactory {

    private final Payment1Client payment1Client;
    private final Payment2Client payment2Client;
    Map<String, PaymentClient> paymentClientMap = new HashMap<>();

    public PaymentClientFactoryImpl(Payment1Client payment1Client, Payment2Client payment2Client) {
        this.payment1Client = payment1Client;
        this.payment2Client = payment2Client;
        paymentClientMap.put("6037", this.payment1Client);
    }

    @Override
    public PaymentClient getPaymentClient(String sourcePan) {
        String sourcePanPrefix = sourcePan.substring(0, 4);
        PaymentClient paymentClient = paymentClientMap.get(sourcePanPrefix);
        if (paymentClient != null) {
            return paymentClient;
        }
        return payment2Client;
    }
}
