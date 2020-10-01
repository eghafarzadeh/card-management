package com.samples.notificationservice.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.samples.notificationservice.service.dto.NotificationDto;
import com.samples.notificationservice.service.dto.NotificationResultDto;
import com.samples.notificationservice.service.dto.SmsPaymentNotificationDto;
import com.samples.notificationservice.service.inf.PaymentNotificationClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

/**
 * @author Elham
 * @since 10/1/2020
 */
@Service
@Slf4j
public class PaymentNotificationClientImpl implements PaymentNotificationClient {

    private final RestTemplate restTemplate;
    @Value("${smsProvider.api.url}")
    private String apiUrl;

    public PaymentNotificationClientImpl(RestTemplate restTemplate) {
        this.restTemplate = restTemplate;
    }

    @Override
    public void processMessage(String message) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            NotificationDto notificationDto = objectMapper.readValue(message, NotificationDto.class);
            SmsPaymentNotificationDto smsPaymentNotificationDto = SmsPaymentNotificationDto.convert(notificationDto);
            restTemplate.postForObject(apiUrl, smsPaymentNotificationDto, NotificationResultDto.class);
        } catch (JsonProcessingException e) {
            log.error("JsonProcessingException in parsing message. Exception message is: " + e);
        }
    }
}
