package com.samples.paymentservice.rest.converter;

import com.samples.paymentservice.rest.model.CardModel;
import com.samples.paymentservice.rest.model.CardTransferRequestModel;
import com.samples.paymentservice.rest.model.TransactionModel;
import com.samples.paymentservice.rest.model.TransactionReportModel;
import com.samples.paymentservice.service.dto.CardDto;
import com.samples.paymentservice.service.dto.CardTransferDto;
import com.samples.paymentservice.service.dto.TransactionDto;
import com.samples.paymentservice.service.dto.TransactionReportDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author Elham
 * @since 9/29/2020
 */
@Mapper(componentModel = "spring")
public interface RestModelConverter {
    List<CardModel> convertCardDtosToCardModels(List<CardDto> cardDtos);

    CardModel convert(CardDto cardDto);

    CardDto convert(CardModel cardModel);

    CardTransferDto convert(CardTransferRequestModel cardTransferRequestModel);

    TransactionModel convert(TransactionDto transactionDto);

    List<TransactionModel> convertTransactionDtosToTransactionModels(List<TransactionDto> transactionDtos);

    List<TransactionReportModel> convertTransactionReportDtosToTransactionReportModels(List<TransactionReportDto> transactionReportDtos);
}
