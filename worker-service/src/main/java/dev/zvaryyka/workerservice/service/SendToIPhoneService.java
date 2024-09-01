package dev.zvaryyka.workerservice.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
import dev.zvaryyka.workerservice.dto.DeviceNotificationDTO;
import dev.zvaryyka.workerservice.dto.NotificationIdDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendToIPhoneService {

    private final NotificationIdServiceProducer notificationIdServiceProducer;

    @Autowired
    public SendToIPhoneService(NotificationIdServiceProducer notificationIdServiceProducer) {
        this.notificationIdServiceProducer = notificationIdServiceProducer;
    }

    public void sendToIPhone(DeviceNotificationDTO dto) {
        try {
            Notification notification = Notification.builder()
                    .setTitle("iPhone Notification")
                    .setBody(dto.getMessageBody())
                    .build();

            Message message = Message.builder()
                    .setToken(dto.getDeviceToken())
                    .setNotification(notification)
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Уведомление отправлено на iPhone с ID: " + response);

            notificationIdServiceProducer.sendNotificationId(
                    new NotificationIdDTO(dto.getNotificationId()));
        } catch (Exception e) {
            System.err.println("Ошибка при отправке уведомления на iPhone: " + e.getMessage());
        }
    }
}
