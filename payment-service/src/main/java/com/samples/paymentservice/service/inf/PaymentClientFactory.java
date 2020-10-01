package com.samples.paymentservice.service.inf;

/**
 * @author Elham
 * @since 10/1/2020
 */
public interface PaymentClientFactory {
    PaymentClient getPaymentClient(String sourcePan);
}
