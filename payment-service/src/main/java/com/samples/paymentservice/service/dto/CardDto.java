package com.samples.paymentservice.service.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author Elham
 * @since 9/29/2020
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class CardDto {
    private Long id;
    private String pan;
}
