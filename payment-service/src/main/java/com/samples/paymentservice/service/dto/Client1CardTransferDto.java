package com.samples.paymentservice.service.dto;

import com.samples.paymentservice.util.StringUtil;
import lombok.Data;

import java.math.BigDecimal;

/**
 * @author Elham
 * @since 9/30/2020
 */
@Data
public class Client1CardTransferDto {
    private String source;
    private String dest;
    private String cvv2;
    private String expDate;
    private String pin;
    private BigDecimal amount;

    public static Client1CardTransferDto convert(CardTransferDto cardTransferDto) {
        Client1CardTransferDto client1CardTransferDto = new Client1CardTransferDto();
        client1CardTransferDto.setSource(StringUtil.separatePan(cardTransferDto.getCardAuthenticationInfo().getPan()));
        client1CardTransferDto.setDest(StringUtil.separatePan(cardTransferDto.getDestinationPan()));
        client1CardTransferDto.setCvv2(cardTransferDto.getCardAuthenticationInfo().getCvv2());
        client1CardTransferDto.setPin(cardTransferDto.getCardAuthenticationInfo().getPin());
        client1CardTransferDto.setExpDate(String.format("%2d%2d", cardTransferDto.getCardAuthenticationInfo().getExpYear(), cardTransferDto.getCardAuthenticationInfo().getExpMonth()));
        client1CardTransferDto.setAmount(cardTransferDto.getAmount());
        return client1CardTransferDto;
    }
}
