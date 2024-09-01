package dev.zvaryyka.workerservice.service;

import com.google.firebase.messaging.FirebaseMessaging;
import com.google.firebase.messaging.Message;
import com.google.firebase.messaging.Notification;
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
        try {
            Notification notification = Notification.builder()
                    .setTitle("Android Notification")
                    .setBody(dto.getMessageBody())
                    .build();

            Message message = Message.builder()
                    .setToken(dto.getDeviceToken())
                    .setNotification(notification)
                    .build();

            String response = FirebaseMessaging.getInstance().send(message);
            System.out.println("Уведомление отправлено на Android с ID: " + response);

            notificationIdServiceProducer.sendNotificationId(
                    new NotificationIdDTO(dto.getNotificationId()));
        } catch (Exception e) {
            System.err.println("Ошибка при отправке уведомления на Android: " + e.getMessage());
        }
    }
}
