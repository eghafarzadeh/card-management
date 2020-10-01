package com.samples.paymentservice.service.inf;

import com.samples.paymentservice.service.dto.CardTransferDto;
import com.samples.paymentservice.service.dto.TransactionDto;

/**
 * @author Elham
 * @since 9/30/2020
 */
public interface PaymentClient {
    TransactionDto cardTransfer(CardTransferDto cardTransferDto);
}
