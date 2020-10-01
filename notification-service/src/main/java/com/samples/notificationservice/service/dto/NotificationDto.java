package com.samples.notificationservice.service.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

/**
 * @author Elham
 * @since 10/2/2020
 */
@Data
@JsonIgnoreProperties
public class NotificationDto {
    private String message;
    private String mobileNumber;
}
