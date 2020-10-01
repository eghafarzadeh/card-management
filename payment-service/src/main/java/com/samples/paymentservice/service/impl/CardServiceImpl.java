package com.samples.paymentservice.service.impl;

import com.samples.paymentservice.exception.CardAndUserNotRelatedException;
import com.samples.paymentservice.exception.CardNotFoundException;
import com.samples.paymentservice.persistance.entity.Card;
import com.samples.paymentservice.persistance.entity.Transaction;
import com.samples.paymentservice.persistance.entity.User;
import com.samples.paymentservice.persistance.repository.CardJpaRepository;
import com.samples.paymentservice.persistance.repository.TransactionJpaRepository;
import com.samples.paymentservice.service.Context;
import com.samples.paymentservice.service.ModelConverter;
import com.samples.paymentservice.service.dto.CardDto;
import com.samples.paymentservice.service.dto.CardTransferDto;
import com.samples.paymentservice.service.dto.TransactionDto;
import com.samples.paymentservice.service.inf.CardService;
import com.samples.paymentservice.service.inf.PaymentClient;
import com.samples.paymentservice.service.inf.PaymentClientFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * @author Elham
 * @since 9/29/2020
 */
@Service
public class CardServiceImpl implements CardService {

    private final CardJpaRepository cardRepository;
    private final TransactionJpaRepository transactionRepository;
    private final ModelConverter modelConverter;
    private final PaymentClientFactory paymentClientFactory;

    public CardServiceImpl(CardJpaRepository cardRepository, TransactionJpaRepository transactionRepository, ModelConverter modelConverter, PaymentClientFactory paymentClientFactory) {
        this.cardRepository = cardRepository;
        this.transactionRepository = transactionRepository;
        this.modelConverter = modelConverter;
        this.paymentClientFactory = paymentClientFactory;
    }

    @Override
    public List<CardDto> findAll(Context context) {
        return modelConverter.convertCardsToCardDtos(cardRepository.findAllByUserId(context.getUserId()));
    }

    @Override
    public CardDto findById(Context context, Long id) {
        return modelConverter.convert(cardRepository.findByIdAndUserId(id, context.getUserId()).orElseThrow(
                () -> new CardNotFoundException("Card with id: " + id + " for user " + context.getUserId() + " not found")));
    }

    @Override
    public CardDto findByPan(Context context, String pan) {
        return modelConverter.convert(cardRepository.findByPanAndUserId(pan, context.getUserId()).orElseThrow(
                () -> new CardNotFoundException("Card with id: " + pan + " for user " + context.getUserId() + " not found")));
    }

    @Override
    public CardDto addCard(Context context, CardDto cardDto) {
        User user = new User();
        user.setId(context.getUserId());
        Card card = modelConverter.convert(cardDto);
        card.setUser(user);
        return modelConverter.convert(cardRepository.save(card));
    }

    @Override
    public CardDto updateCard(Context context, CardDto cardDto) {
        User user = new User();
        user.setId(context.getUserId());
        Card card = modelConverter.convert(cardDto);
        return modelConverter.convert(cardRepository.save(card));
    }

    @Override
    public void deleteById(Context context, Long id) {
        /* todo */
        cardRepository.deleteById(id);
    }

    @Override
    public TransactionDto cardToCardTransfer(Context context, CardTransferDto cardTransferDto) {
        /*1- check if card is in user card list
         * 2- get source pan and choose provider*/
        Optional<Card> cardOptional = cardRepository.findByUserIdAndPan(context.getUserId(), cardTransferDto.getCardAuthenticationInfo().getPan());
        if (!cardOptional.isPresent()) {
            throw new CardAndUserNotRelatedException("Card doesn't include in user's cards");
        }
        PaymentClient paymentClient = paymentClientFactory.getPaymentClient(cardTransferDto.getCardAuthenticationInfo().getPan());
        TransactionDto transactionDto = paymentClient.cardTransfer(cardTransferDto);
        Transaction transaction = modelConverter.convert(transactionDto);
        User user = new User();
        user.setId(context.getUserId());
        transaction.setUser(user);
        return modelConverter.convert(transactionRepository.save(transaction));
    }
}
