package com.samples.paymentservice.exception;

import javax.persistence.EntityNotFoundException;

/**
 * @author Elham
 * @since 6/26/2020
 */
public class CardNotFoundException extends EntityNotFoundException {
    public CardNotFoundException(String msg) {
        super(msg);
    }
}
