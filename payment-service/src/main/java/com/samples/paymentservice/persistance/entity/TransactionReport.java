package com.samples.paymentservice.persistance.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Elham
 * @since 10/1/2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TransactionReport {
    private String source;
    private TransactionStatus status;
    private Long count;
}
