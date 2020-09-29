package com.samples.paymentservice.rest;

import com.samples.paymentservice.rest.controller.CardController;
import com.samples.paymentservice.rest.model.CardModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.server.RepresentationModelAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Elham
 * @since 7/18/2020
 */
@Component
public class CardModelAssembler implements RepresentationModelAssembler<CardModel, EntityModel<CardModel>> {
    @Override
    public EntityModel<CardModel> toModel(CardModel cardModel) {
        return EntityModel.of(cardModel, linkTo(methodOn(CardController.class).findById(cardModel.getId())).withSelfRel(),
                linkTo(methodOn(CardController.class).all()).withRel("cards"));
    }
}
