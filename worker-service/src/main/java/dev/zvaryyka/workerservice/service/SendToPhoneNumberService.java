package dev.zvaryyka.workerservice.service;

import dev.zvaryyka.workerservice.dto.NotificationIdDTO;
import dev.zvaryyka.workerservice.dto.SmsNotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendToPhoneNumberService {

    private final NotificationIdServiceProducer notificationIdServiceProducer;

    @Autowired
    public SendToPhoneNumberService(NotificationIdServiceProducer notificationIdServiceProducer) {
        this.notificationIdServiceProducer = notificationIdServiceProducer;
    }

    public void sendToPhoneNumber(SmsNotificationDTO dto) {

        notificationIdServiceProducer.sendNotificationId(
                new NotificationIdDTO(dto.getNotificationId()));
        System.out.println("Сообщение с ID отправлено");
    }
}
