package dev.zvaryyka.workerservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.zvaryyka.workerservice.dto.NotificationIdDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationIdServiceProducer {

    private final KafkaTemplate<String, String> kafkaTemplate;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Autowired
    public NotificationIdServiceProducer(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void sendNotificationId(NotificationIdDTO notificationIdDTO) {
        try {
            String message = objectMapper.writeValueAsString(notificationIdDTO);
            kafkaTemplate.send("notification-id-topic", message);
            System.out.println("Sent Notification ID: " + message);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
