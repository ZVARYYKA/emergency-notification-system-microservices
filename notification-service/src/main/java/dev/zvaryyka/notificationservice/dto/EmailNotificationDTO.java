package dev.zvaryyka.notificationservice.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailNotificationDTO {
    private String notificationId;
    private String email;
    private String subject;
    private String body;
}


