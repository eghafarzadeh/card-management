package com.samples.paymentservice.service.impl;

import com.samples.paymentservice.persistance.entity.PaymentClientName;
import com.samples.paymentservice.service.dto.CardTransferDto;
import com.samples.paymentservice.service.dto.Client2CardTransferDto;
import com.samples.paymentservice.service.dto.TransactionDto;
import com.samples.paymentservice.service.inf.PaymentClient;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Elham
 * @since 9/30/2020
 */
@Service
public class Payment2Client implements PaymentClient {
    @Value("${paymentProvider2.api.url}")
    private String apiUrl;

    private final RestTemplate restTemplate;

    public Payment2Client(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    /*todo: api probably returns another dto. But it doesn't specified in api sec. So I assumed it is same as CardTransferResponseDto  */
    @Override
    public TransactionDto cardTransfer(CardTransferDto cardTransferDto) {

        Client2CardTransferDto client2CardTransferDto = Client2CardTransferDto.convert(cardTransferDto);
        TransactionDto transactionResult = restTemplate.postForObject(apiUrl, client2CardTransferDto, TransactionDto.class);
        /*this block must be deleted. Now this is here because I couldn't find a way to returning params from body in wireMock*/
        if (transactionResult != null) {
            if (transactionResult.getAmount() == null) {
                transactionResult.setAmount(cardTransferDto.getAmount());
            }
            if (transactionResult.getDestination() == null) {
                transactionResult.setDestination(cardTransferDto.getDestinationPan());
            }
            if (transactionResult.getSource() == null) {
                transactionResult.setSource(cardTransferDto.getCardAuthenticationInfo().getPan());
            }
            transactionResult.setPaymentClientName(PaymentClientName.PAYMENT2);
        }
        return transactionResult;
    }
}
