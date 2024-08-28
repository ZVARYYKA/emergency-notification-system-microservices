package dev.zvaryyka.workerservice.service;

import dev.zvaryyka.workerservice.dto.DeviceNotificationDTO;
import dev.zvaryyka.workerservice.dto.NotificationIdDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendToAndroidService {
    private final NotificationIdServiceProducer notificationIdServiceProducer;

    @Autowired
    public SendToAndroidService(NotificationIdServiceProducer notificationIdServiceProducer) {
        this.notificationIdServiceProducer = notificationIdServiceProducer;
    }

    public void sendToAndroid(DeviceNotificationDTO dto) {
        notificationIdServiceProducer.sendNotificationId(
                new NotificationIdDTO(dto.getNotificationId()));
        System.out.println("Сообщение с ID отправлено");
    }
}
