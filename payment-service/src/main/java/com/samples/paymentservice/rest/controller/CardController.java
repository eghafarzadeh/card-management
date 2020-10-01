package com.samples.paymentservice.rest.controller;

import com.samples.paymentservice.rest.converter.CardModelAssembler;
import com.samples.paymentservice.rest.converter.RestModelConverter;
import com.samples.paymentservice.rest.model.CardModel;
import com.samples.paymentservice.rest.model.CardTransferRequestModel;
import com.samples.paymentservice.rest.model.TransactionModel;
import com.samples.paymentservice.util.ContextUtil;
import com.samples.paymentservice.service.Context;
import com.samples.paymentservice.service.dto.CardTransferDto;
import com.samples.paymentservice.service.inf.CardService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.IanaLinkRelations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

/**
 * @author Elham
 * @since 9/29/2020
 */
@RestController
@RequestMapping("/api/card")
public class CardController {


    private final RestModelConverter modelConverter;
    private final CardModelAssembler cardModelAssembler;
    private final CardService cardService;

    @Autowired
    public CardController(RestModelConverter modelConverter, CardModelAssembler cardModelAssembler, CardService cardService) {
        this.modelConverter = modelConverter;
        this.cardModelAssembler = cardModelAssembler;
        this.cardService = cardService;
    }

    @GetMapping("")
    public CollectionModel<EntityModel<CardModel>> all() {
        Context context = ContextUtil.getContext();
        List<CardModel> cardModels = modelConverter.convertCardDtosToCardModels(cardService.findAll(context));
        if (cardModels == null) {
            return null;
        }
        List<EntityModel<CardModel>> cards = cardModels.stream().map(cardModelAssembler::toModel)
                .collect(Collectors.toList());
        return CollectionModel.of(cards, linkTo(methodOn(CardController.class).all()).withSelfRel());
    }

    @GetMapping("/{pan}")
    public EntityModel<CardModel> findByPan(@PathVariable String pan) {
        Context context = ContextUtil.getContext();
        return cardModelAssembler.toModel(modelConverter.convert(cardService.findByPan(context, pan)));
    }

    @PostMapping("")
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseEntity<EntityModel<CardModel>> addCard(@Valid @RequestBody CardModel newCard) {
        Context context = ContextUtil.getContext();
        EntityModel<CardModel> entityModel = cardModelAssembler.toModel(modelConverter.convert(cardService.addCard(context, modelConverter.convert(newCard))));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);

    }

    @PutMapping("")
    @ResponseStatus(HttpStatus.OK)
    public ResponseEntity<EntityModel<CardModel>> updateCard(@Valid @RequestBody CardModel card) {
        Context context = ContextUtil.getContext();
        EntityModel<CardModel> entityModel = cardModelAssembler.toModel(modelConverter.convert(cardService.updateCard(context, modelConverter.convert(card))));
        return ResponseEntity
                .created(entityModel.getRequiredLink(IanaLinkRelations.SELF).toUri())
                .body(entityModel);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCard(@PathVariable Long id) {
        Context context = ContextUtil.getContext();
        cardService.deleteById(context, id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/transfer")
    @ResponseStatus(HttpStatus.CREATED)
    public TransactionModel transfer(@Valid @RequestBody CardTransferRequestModel cardTransferRequestModel) {
        Context context = ContextUtil.getContext();
        CardTransferDto cardTransferDto = modelConverter.convert(cardTransferRequestModel);
        return modelConverter.convert(cardService.cardToCardTransfer(context, cardTransferDto));
    }

}
