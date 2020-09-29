package com.samples.paymentservice.rest;

import com.samples.paymentservice.rest.model.CardModel;
import com.samples.paymentservice.service.dto.CardDto;
import org.mapstruct.Mapper;

import java.util.List;

/**
 * @author Elham
 * @since 9/29/2020
 */
@Mapper(componentModel = "spring")
public interface RestModelConverter {
    List<CardModel> convert(List<CardDto> movieDtos);

    CardModel convert(CardDto movieDto);

    CardDto convert(CardModel movieModel);
}
