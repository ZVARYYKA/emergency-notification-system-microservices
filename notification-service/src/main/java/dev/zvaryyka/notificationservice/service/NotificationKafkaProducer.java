package dev.zvaryyka.notificationservice.service;


import dev.zvaryyka.notificationservice.dto.DeviceNotificationDTO;
import dev.zvaryyka.notificationservice.dto.EmailNotificationDTO;
import dev.zvaryyka.notificationservice.dto.SmsNotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class NotificationKafkaProducer {

    private final KafkaTemplate<String, DeviceNotificationDTO> deviceKafkaTemplate;
    private final KafkaTemplate<String, EmailNotificationDTO> emailKafkaTemplate;
    private final KafkaTemplate<String, SmsNotificationDTO> smsKafkaTemplate;

    @Autowired
    public NotificationKafkaProducer(KafkaTemplate<String, DeviceNotificationDTO> deviceKafkaTemplate,
                                     KafkaTemplate<String, EmailNotificationDTO> emailKafkaTemplate,
                                     KafkaTemplate<String, SmsNotificationDTO> smsKafkaTemplate) {
        this.deviceKafkaTemplate = deviceKafkaTemplate;
        this.emailKafkaTemplate = emailKafkaTemplate;
        this.smsKafkaTemplate = smsKafkaTemplate;
    }

    public void sendDeviceNotification(DeviceNotificationDTO notification) {
        try {
            deviceKafkaTemplate.send("device-notifications", notification);
        } catch (Exception e) {
            System.err.println("Failed to send device notification: " + e.getMessage());
        }
    }

    public void sendEmailNotification(EmailNotificationDTO notification) {
        try {
            emailKafkaTemplate.send("email-notifications", notification);
        } catch (Exception e) {
            System.err.println("Failed to send email notification: " + e.getMessage());
        }
    }

    public void sendSmsNotification(SmsNotificationDTO notification) {
        try {
            smsKafkaTemplate.send("sms-notifications", notification);
        } catch (Exception e) {
            System.err.println("Failed to send SMS notification: " + e.getMessage());
        }
    }
}

