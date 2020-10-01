package com.samples.paymentservice.exception;

/**
 * @author Elham
 * @since 9/30/2020
 */
public class CardAndUserNotRelatedException extends RuntimeException {
    public CardAndUserNotRelatedException(String message) {
        super(message);
    }
}
