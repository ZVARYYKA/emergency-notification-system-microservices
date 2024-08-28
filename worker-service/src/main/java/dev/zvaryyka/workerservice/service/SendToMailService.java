package dev.zvaryyka.workerservice.service;

import dev.zvaryyka.workerservice.dto.EmailNotificationDTO;
import dev.zvaryyka.workerservice.dto.NotificationIdDTO;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendToMailService {
    private final NotificationIdServiceProducer notificationIdServiceProducer;

    @Autowired
    public SendToMailService(NotificationIdServiceProducer notificationIdServiceProducer) {
        this.notificationIdServiceProducer = notificationIdServiceProducer;
    }

    public void sendToMail(EmailNotificationDTO dto) {
        notificationIdServiceProducer.sendNotificationId(
                new NotificationIdDTO(dto.getNotificationId()));
        System.out.println("Сообщение с ID отправлено");
    }
}
