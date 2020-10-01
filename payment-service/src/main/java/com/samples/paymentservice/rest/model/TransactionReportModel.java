package com.samples.paymentservice.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Elham
 * @since 10/1/2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionReportModel {
    private String source;
    private long successfulTransactionCount;
    private long unSuccessfulTransactionCount;
}
