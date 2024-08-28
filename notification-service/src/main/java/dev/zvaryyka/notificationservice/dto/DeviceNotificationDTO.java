package dev.zvaryyka.notificationservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DeviceNotificationDTO {
    private String notificationId;
    private String deviceToken;
    private String deviceType; // Android или iPhone
    private String messageBody;
}

