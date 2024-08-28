package dev.zvaryyka.notificationservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class SmsNotificationDTO {
    private String notificationId;
    private String phoneNumber;
    private String message;
}
