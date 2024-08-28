package dev.zvaryyka.workerservice.dto;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class EmailNotificationDTO {
    private String notificationId;
    private String email;
    private String subject;
    private String body;
}


