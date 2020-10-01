package com.samples.paymentservice.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.samples.paymentservice.persistance.entity.PaymentClientName;
import com.samples.paymentservice.persistance.entity.TransactionStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

/**
 * @author Elham
 * @since 9/30/2020
 */
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TransactionDto {
    private Date transactionDate;
    private String referenceNumber;
    private String source;
    private String destination;
    private BigDecimal amount;
    private TransactionStatus status;
    private PaymentClientName paymentClientName;
}
