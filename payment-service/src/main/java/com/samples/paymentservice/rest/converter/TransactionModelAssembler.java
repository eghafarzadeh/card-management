package com.samples.paymentservice.rest.converter;

import com.samples.paymentservice.rest.controller.CardController;
import com.samples.paymentservice.rest.controller.TransactionController;
import com.samples.paymentservice.rest.model.TransactionModel;
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
public class TransactionModelAssembler implements RepresentationModelAssembler<TransactionModel, EntityModel<TransactionModel>> {

    @Override
    public EntityModel<TransactionModel> toModel(TransactionModel transactionModel) {
        return EntityModel.of(transactionModel, linkTo(methodOn(TransactionController.class).findByReferenceNumber(transactionModel.getReferenceNumber())).withSelfRel(),
                linkTo(methodOn(CardController.class).all()).withRel("transactions"));
    }
}
