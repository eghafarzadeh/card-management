package com.samples.paymentservice.service;

import com.samples.paymentservice.persistance.entity.Card;
import com.samples.paymentservice.persistance.entity.Transaction;
import com.samples.paymentservice.service.dto.CardDto;
import com.samples.paymentservice.service.dto.TransactionDto;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ModelConverter {
    //    UserDto convert(User user);
    CardDto convert(Card card);

    Card convert(CardDto cardDto);

    List<CardDto> convertCardsToCardDtos(List<Card> cards);

    Transaction convert(TransactionDto transactionDto);

    TransactionDto convert(Transaction transaction);

    List<TransactionDto> convertTransactionsToTransactionDtos(List<Transaction> transactions);
}
