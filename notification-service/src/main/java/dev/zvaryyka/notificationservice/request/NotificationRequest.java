package dev.zvaryyka.notificationservice.request;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@NoArgsConstructor
@Getter
@Setter
public class NotificationRequest {

    private UUID groupId;

    private UUID templateId;


}
