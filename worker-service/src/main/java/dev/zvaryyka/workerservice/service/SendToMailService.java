package dev.zvaryyka.workerservice.service;

import dev.zvaryyka.workerservice.dto.EmailDetails;
import dev.zvaryyka.workerservice.dto.EmailNotificationDTO;
import dev.zvaryyka.workerservice.dto.NotificationIdDTO;
import lombok.Getter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SendToMailService {
    private final NotificationIdServiceProducer notificationIdServiceProducer;
    private final EmailService emailService;

    @Autowired
    public SendToMailService(NotificationIdServiceProducer notificationIdServiceProducer, EmailService emailService) {
        this.notificationIdServiceProducer = notificationIdServiceProducer;
        this.emailService = emailService;
    }

    public void sendToMail(EmailNotificationDTO dto) {
        emailService.sendSimpleMail(new EmailDetails(
                dto.getEmail(),
                dto.getBody(),
                dto.getSubject()
        ));
        notificationIdServiceProducer.sendNotificationId(
                new NotificationIdDTO(dto.getNotificationId()));
        System.out.println("Сообщение с ID отправлено");
    }
}
