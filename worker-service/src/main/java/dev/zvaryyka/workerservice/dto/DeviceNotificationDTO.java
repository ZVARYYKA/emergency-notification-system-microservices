package dev.zvaryyka.workerservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class DeviceNotificationDTO {
    private String notificationId;
    private String deviceToken;
    private String deviceType; // Android или iPhone
    private String messageBody;
}

