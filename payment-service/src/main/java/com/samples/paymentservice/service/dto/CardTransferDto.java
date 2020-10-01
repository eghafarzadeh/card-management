package com.samples.paymentservice.service.dto;

import com.samples.paymentservice.rest.model.CardAuthenticationInfo;
import lombok.Data;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Pattern;
import java.math.BigDecimal;

/**
 * @author Elham
 * @since 9/30/2020
 */
@Data
public class CardTransferDto {
    private Long cardId;

    private String username;

    //    @Pattern(regexp="${pan.pattern}")
    @Pattern(regexp = "\\d{16}|\\d{19}")
    @NotEmpty
    private String destinationPan;

    @NotEmpty
    @Min(value = 0)
    private BigDecimal amount;

    private CardAuthenticationInfo cardAuthenticationInfo;
}
