package com.samples.paymentservice.service.inf;

import com.samples.paymentservice.persistance.entity.Transaction;
import com.samples.paymentservice.service.Context;
import com.samples.paymentservice.service.dto.CardDto;
import com.samples.paymentservice.service.dto.CardTransferDto;
import com.samples.paymentservice.service.dto.TransactionDto;

import java.util.List;

/**
 * @author Elham
 * @since 9/29/2020
 */
public interface CardService {
    List<CardDto> findAll(Context context);

    CardDto findById(Context context, Long id);

    CardDto findByPan(Context context, String pan);

    CardDto addCard(Context context, CardDto card);

    CardDto updateCard(Context context, CardDto card);

    void deleteById(Context context, Long id);

    TransactionDto cardToCardTransfer(Context context, CardTransferDto cardTransferDto);
}
