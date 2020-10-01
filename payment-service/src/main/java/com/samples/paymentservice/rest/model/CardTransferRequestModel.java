package com.samples.paymentservice.rest.model;

import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * @author Elham
 * @since 9/30/2020
 */
@Data
public class CardTransferRequestModel {
    private Long cardId;

    //    @Pattern(regexp="${pan.pattern}")
    @Pattern(regexp = "\\d{16}|\\d{19}", message = "destinationPan must be 16 digit or 19 digit")
    @NotNull(message = "destinationPan must have value")
    private String destinationPan;

    @NotNull
    @Min(value = 0)
    private BigDecimal amount;

    private CardAuthenticationInfo cardAuthenticationInfo;
}
