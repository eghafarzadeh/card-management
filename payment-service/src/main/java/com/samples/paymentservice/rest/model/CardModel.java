package com.samples.paymentservice.rest.model;

import lombok.Data;

import javax.validation.constraints.Pattern;

/**
 * @author Elham
 * @since 9/29/2020
 */
@Data
public class CardModel {

    private Long id;

    @Pattern(regexp = "\\d{16}|\\d{19}")
    private String pan;

}
