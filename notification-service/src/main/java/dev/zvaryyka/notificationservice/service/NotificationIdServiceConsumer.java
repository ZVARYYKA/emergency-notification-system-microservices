package dev.zvaryyka.notificationservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.zvaryyka.notificationservice.dto.NotificationIdDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationIdServiceConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper();

    private final NotificationService notificationService;

    @Autowired
    public NotificationIdServiceConsumer(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @KafkaListener(topics = "notification-id-topic", groupId = "notification-id-group")
    public void consumeNotificationId(String message) {
        try {
            NotificationIdDTO notificationIdDTO = objectMapper.readValue(message, NotificationIdDTO.class);
            System.out.println("Received Notification ID: " + notificationIdDTO.getNotificationId());

            notificationService.changeStatusToSent(notificationIdDTO);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
