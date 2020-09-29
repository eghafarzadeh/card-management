package com.samples.paymentservice.service.impl;

import com.samples.paymentservice.exception.CardNotFoundException;
import com.samples.paymentservice.persistance.repository.CardJpaRepository;
import com.samples.paymentservice.service.ModelConverter;
import com.samples.paymentservice.service.dto.CardDto;
import com.samples.paymentservice.service.inf.CardService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Elham
 * @since 9/29/2020
 */
@Service
public class CardServiceImpl implements CardService {

    private final CardJpaRepository cardRepository;
    private final ModelConverter modelConverter;

    public CardServiceImpl(CardJpaRepository cardRepository, ModelConverter modelConverter) {
        this.cardRepository = cardRepository;
        this.modelConverter = modelConverter;
    }

    @Override
    public List<CardDto> findAll() {
        return modelConverter.convert(cardRepository.findAll());
    }

    @Override
    public CardDto findById(Long id) {
        return modelConverter.convert(cardRepository.findById(id).orElseThrow(
                () -> new CardNotFoundException("Card with id: " + id + " not found")));
    }

    @Override
    public CardDto addCard(CardDto card) {
        return modelConverter.convert(cardRepository.save(modelConverter.convert(card)));
    }

    @Override
    public CardDto updateCard(CardDto card) {
        return modelConverter.convert(cardRepository.save(modelConverter.convert(card)));
    }

    @Override
    public void deleteById(Long id) {
        /* todo */
        cardRepository.deleteById(id);
    }
}
