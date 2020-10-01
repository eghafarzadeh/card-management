package com.samples.paymentservice.rest.model;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

/**
 * @author Elham
 * @since 9/30/2020
 */
@Data
public class CardAuthenticationInfo {

    @Pattern(regexp = "\\d{16}|\\d{19}", message = "pan must be 16 digit or 19 digit")
    @NotNull(message = "pan must have value")
    private String pan;

    @Pattern(regexp = "\\d{3}|\\d{4}")
    @NotEmpty
    private String cvv2;

    @Pattern(regexp = "\\d{2}")
    private int expYear;

    @Pattern(regexp = "\\d{2}")
    private int expMonth;

    @Pattern(regexp = "\\d{6,10}") /*todo*/
    @NotEmpty
    private String pin;
}
