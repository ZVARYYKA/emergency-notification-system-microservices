package dev.zvaryyka.workerservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class SmsNotificationDTO {
    private String notificationId;
    private String phoneNumber;
    private String message;
}
