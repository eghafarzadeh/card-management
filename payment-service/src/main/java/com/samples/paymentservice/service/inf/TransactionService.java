package com.samples.paymentservice.service.inf;

import com.samples.paymentservice.persistance.entity.TransactionReport;
import com.samples.paymentservice.service.Context;
import com.samples.paymentservice.service.dto.TransactionDto;
import com.samples.paymentservice.service.dto.TransactionReportDto;

import java.util.Date;
import java.util.List;

/**
 * @author Elham
 * @since 10/1/2020
 */
public interface TransactionService {
    List<TransactionDto> findAll(Context context);

    TransactionDto findByReferenceNumber(Context context, String referenceNumber);

    List<TransactionReportDto> getTransactionReports(Context context, Date from, Date to);
}
