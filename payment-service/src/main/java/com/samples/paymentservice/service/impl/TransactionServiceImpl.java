package com.samples.paymentservice.service.impl;

import com.samples.paymentservice.exception.CardNotFoundException;
import com.samples.paymentservice.persistance.entity.TransactionReport;
import com.samples.paymentservice.persistance.entity.TransactionStatus;
import com.samples.paymentservice.persistance.repository.TransactionJpaRepository;
import com.samples.paymentservice.service.Context;
import com.samples.paymentservice.service.ModelConverter;
import com.samples.paymentservice.service.dto.TransactionDto;
import com.samples.paymentservice.service.dto.TransactionReportDto;
import com.samples.paymentservice.service.inf.TransactionService;
import org.springframework.stereotype.Service;

import java.util.*;

/**
 * @author Elham
 * @since 10/1/2020
 */
@Service
public class TransactionServiceImpl implements TransactionService {
    private final TransactionJpaRepository transactionRepository;
    private final ModelConverter modelConverter;

    public TransactionServiceImpl(TransactionJpaRepository transactionRepository, ModelConverter modelConverter) {
        this.transactionRepository = transactionRepository;
        this.modelConverter = modelConverter;
    }

    @Override
    public List<TransactionDto> findAll(Context context) {
        return modelConverter.convertTransactionsToTransactionDtos(transactionRepository.findAll());
    }

    @Override
    public TransactionDto findByReferenceNumber(Context context, String referenceNumber) {
        return modelConverter.convert(transactionRepository.findByReferenceNumberAndUserId(referenceNumber, context.getUserId()).orElseThrow(
                () -> new CardNotFoundException("Transaction with reference number: " + referenceNumber + " for user " + context.getUserId() + " not found")));
    }

    @Override
    public List<TransactionReportDto> getTransactionReports(Context context, Date from, Date to) {
        List<TransactionReport> transactionReports = transactionRepository.reportStatusCounts(from, to, context.getUserId());
        Map<String, TransactionReportDto> transactionReportDtoMap = new HashMap<>();
        for (TransactionReport transactionReport : transactionReports) {
            TransactionReportDto transactionReportDto = transactionReportDtoMap.get(transactionReport.getSource());
            if (transactionReportDto == null) {
                transactionReportDto = new TransactionReportDto(transactionReport.getSource());
            }
            if (transactionReport.getStatus() == TransactionStatus.SUCCESSFUL) {
                transactionReportDto.setSuccessfulTransactionCount(transactionReport.getCount());
            } else if (transactionReport.getStatus() == TransactionStatus.FAILED) {
                transactionReportDto.setUnSuccessfulTransactionCount(transactionReport.getCount());
            }
            transactionReportDtoMap.put(transactionReport.getSource(), transactionReportDto);
        }
        return new ArrayList<>(transactionReportDtoMap.values());
    }
}
