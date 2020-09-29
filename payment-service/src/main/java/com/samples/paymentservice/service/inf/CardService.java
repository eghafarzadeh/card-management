package com.samples.paymentservice.service.inf;

import com.samples.paymentservice.service.dto.CardDto;

import java.util.List;

/**
 * @author Elham
 * @since 9/29/2020
 */
public interface CardService {
    List<CardDto> findAll();

    CardDto findById(Long id);

    CardDto addCard(CardDto card);

    CardDto updateCard(CardDto card);

    void deleteById(Long id);
}
