package com.samples.paymentservice.service.dto;

import com.samples.paymentservice.util.StringUtil;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Elham
 * @since 9/30/2020
 */
@Data
public class Client2CardTransferDto {
    private String source;
    private String target;
    private String cvv2;
    private String expire;
    private String pin2;
    private BigDecimal amount;

    public static Client2CardTransferDto convert(CardTransferDto cardTransferDto) {
        Client2CardTransferDto client2CardTransferDto = new Client2CardTransferDto();
        client2CardTransferDto.setSource(StringUtil.separatePan(cardTransferDto.getCardAuthenticationInfo().getPan()));
        client2CardTransferDto.setTarget(StringUtil.separatePan(cardTransferDto.getDestinationPan()));
        client2CardTransferDto.setCvv2(cardTransferDto.getCardAuthenticationInfo().getCvv2());
        client2CardTransferDto.setPin2(cardTransferDto.getCardAuthenticationInfo().getPin());
        client2CardTransferDto.setExpire(String.format("%2d/%2d", cardTransferDto.getCardAuthenticationInfo().getExpYear(), cardTransferDto.getCardAuthenticationInfo().getExpMonth()));
        client2CardTransferDto.setAmount(cardTransferDto.getAmount());
        return client2CardTransferDto;
    }
}
