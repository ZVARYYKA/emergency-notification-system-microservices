package dev.zvaryyka.workerservice.service;

import com.twilio.Twilio;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import dev.zvaryyka.workerservice.dto.NotificationIdDTO;
import dev.zvaryyka.workerservice.dto.SmsNotificationDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendToPhoneNumberService {

    private final NotificationIdServiceProducer notificationIdServiceProducer;

    // TODO Twilio credentials
    public static final String ACCOUNT_SID = "*****";
    public static final String AUTH_TOKEN = "*****";
    private static final String FROM_NUMBER = "********";

    @Autowired
    public SendToPhoneNumberService(NotificationIdServiceProducer notificationIdServiceProducer) {
        this.notificationIdServiceProducer = notificationIdServiceProducer;
        Twilio.init(ACCOUNT_SID, AUTH_TOKEN);
    }

    public void sendToPhoneNumber(SmsNotificationDTO dto) {
        try {
            // Создание и отправка SMS сообщения
            Message message = Message.creator(
                    new PhoneNumber(dto.getPhoneNumber()),   // Номер получателя
                    new PhoneNumber(FROM_NUMBER),           // Номер отправителя
                    dto.getMessage()                        // Текст сообщения
            ).create();

            // Проверка успешности отправки
            if (message.getSid() != null) {
                notificationIdServiceProducer.sendNotificationId(
                        new NotificationIdDTO(dto.getNotificationId()));
                System.out.println("Сообщение с ID " + dto.getNotificationId() + " отправлено успешно. SID: " + message.getSid());
            } else {
                System.err.println("Ошибка при отправке сообщения, SID не получен.");
                //TODO Add send to kafka about fail sms
            }
        } catch (Exception e) {
            System.err.println("Произошла ошибка при отправке SMS: " + e.getMessage());

        }
    }
}
