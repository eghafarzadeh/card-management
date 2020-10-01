package com.samples.notificationservice.service.dto;

import lombok.Data;

/**
 * @author Elham
 * @since 10/2/2020
 */
@Data
public class SmsPaymentNotificationDto {
    private String msg;
    private String target;

    public static SmsPaymentNotificationDto convert(NotificationDto notificationDto) {
        SmsPaymentNotificationDto smsPaymentNotificationDto = new SmsPaymentNotificationDto();
        smsPaymentNotificationDto.setMsg(notificationDto.getMessage());
        smsPaymentNotificationDto.setTarget(notificationDto.getMobileNumber());
        return smsPaymentNotificationDto;
    }
}
