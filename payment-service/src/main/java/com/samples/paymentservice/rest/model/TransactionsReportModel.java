package com.samples.paymentservice.rest.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;
import java.util.List;

/**
 * @author Elham
 * @since 10/1/2020
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class TransactionsReportModel {
    private Date from;
    private Date to;
    private List<TransactionReportModel> transactions;
}
