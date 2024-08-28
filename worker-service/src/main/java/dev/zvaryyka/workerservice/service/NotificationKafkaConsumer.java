package dev.zvaryyka.workerservice.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import dev.zvaryyka.workerservice.dto.DeviceNotificationDTO;
import dev.zvaryyka.workerservice.dto.EmailNotificationDTO;
import dev.zvaryyka.workerservice.dto.SmsNotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
public class NotificationKafkaConsumer {

    private final ObjectMapper objectMapper = new ObjectMapper(); //TODO

    private final SendToAndroidService sendToAndroidService;
    private final SendToIPhoneService sendToIPhoneService;
    private final SendToMailService sendToMailService;
    private final SendToPhoneNumberService sendToPhoneNumberService;

    @Autowired
    public NotificationKafkaConsumer(SendToAndroidService sendToAndroidService, SendToIPhoneService sendToIPhoneService, SendToMailService sendToMailService, SendToPhoneNumberService sendToPhoneNumberService) {
        this.sendToAndroidService = sendToAndroidService;
        this.sendToIPhoneService = sendToIPhoneService;
        this.sendToMailService = sendToMailService;
        this.sendToPhoneNumberService = sendToPhoneNumberService;
    }


    @KafkaListener(topics = "email-notifications", groupId = "email-group")
    public void consumeEmailNotification(String message) {
        try {
            EmailNotificationDTO dto = objectMapper.readValue(message, EmailNotificationDTO.class);
            System.out.println("Received Email Notification: " + dto);
           sendToMailService.sendToMail(dto);
        } catch (Exception e) {
            e.printStackTrace(); // Логирование ошибки десериализации
        }
    }

    @KafkaListener(topics = "device-notifications", groupId = "device-group")
    public void consumeDeviceNotification(String message) {
        try {
            DeviceNotificationDTO dto = objectMapper.readValue(message, DeviceNotificationDTO.class);
            System.out.println("Received Device Notification: " + dto);
            if (dto.getDeviceType().equals("Android")) {
                sendToAndroidService.sendToAndroid(dto);
            }
            if(dto.getDeviceType().equals("iPhone")){
                sendToIPhoneService.sendToIPhone(dto);
            }
        } catch (Exception e) {
            e.printStackTrace(); // Логирование ошибки десериализации
        }
    }

    @KafkaListener(topics = "sms-notifications", groupId = "sms-group")
    public void consumeSmsNotification(String message) {
        try {
            SmsNotificationDTO dto = objectMapper.readValue(message, SmsNotificationDTO.class);
            System.out.println("Received SMS Notification: " + dto);
            sendToPhoneNumberService.sendToPhoneNumber(dto);
        } catch (Exception e) {
            e.printStackTrace(); // Логирование ошибки десериализации
        }
    }
}
