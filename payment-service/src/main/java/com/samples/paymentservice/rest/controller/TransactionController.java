package com.samples.paymentservice.rest.controller;

import com.samples.paymentservice.rest.converter.RestModelConverter;
import com.samples.paymentservice.rest.converter.TransactionModelAssembler;
import com.samples.paymentservice.rest.model.TransactionModel;
import com.samples.paymentservice.rest.model.TransactionReportModel;
import com.samples.paymentservice.rest.model.TransactionsReportModel;
import com.samples.paymentservice.util.ContextUtil;
import com.samples.paymentservice.service.Context;
import com.samples.paymentservice.service.inf.TransactionService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Elham
 * @since 9/30/2020
 */
@RestController
@RequestMapping("/api/transaction")
public class TransactionController {

    private final RestModelConverter modelConverter;
    private final TransactionService transactionService;
    private final TransactionModelAssembler transactionModelAssembler;

    public TransactionController(RestModelConverter modelConverter, TransactionService transactionService, TransactionModelAssembler transactionModelAssembler) {
        this.modelConverter = modelConverter;
        this.transactionService = transactionService;
        this.transactionModelAssembler = transactionModelAssembler;
    }

    @GetMapping("")
    public CollectionModel<EntityModel<TransactionModel>> all() {
        Context context = ContextUtil.getContext();
        List<TransactionModel> transactionModels = modelConverter.convertTransactionDtosToTransactionModels(transactionService.findAll(context));

        if (transactionModels == null) {
            return null;
        }
        List<EntityModel<TransactionModel>> transactions = transactionModels.stream().map(transactionModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(transactions, linkTo(methodOn(TransactionController.class).all()).withSelfRel());
    }

    @GetMapping("/{referenceNumber}")
    public EntityModel<TransactionModel> findByReferenceNumber(@PathVariable String referenceNumber) {
        Context context = ContextUtil.getContext();
        return transactionModelAssembler.toModel(modelConverter.convert(transactionService.findByReferenceNumber(context, referenceNumber)));
    }

    @GetMapping("/{from}/{to}")
    public EntityModel<TransactionsReportModel> transactionReport(@PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date from,
                                                                  @PathVariable @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME) Date to) {
        Context context = ContextUtil.getContext();
        TransactionsReportModel transactionsReportModel = new TransactionsReportModel();
        List<TransactionReportModel> transactionReportModels = modelConverter.convertTransactionReportDtosToTransactionReportModels(transactionService.getTransactionReports(context, from, to));
        transactionsReportModel.setFrom(from);
        transactionsReportModel.setTo(to);
        transactionsReportModel.setTransactions(transactionReportModels);
        transactionService.getTransactionReports(context, from, to);
        return EntityModel.of(transactionsReportModel);
    }

}
