package dev.zvaryyka.notificationservice.service;

import dev.zvaryyka.notificationservice.dto.DeviceNotificationDTO;
import dev.zvaryyka.notificationservice.dto.EmailNotificationDTO;
import dev.zvaryyka.notificationservice.dto.NotificationIdDTO;
import dev.zvaryyka.notificationservice.dto.SmsNotificationDTO;
import dev.zvaryyka.notificationservice.models.Notification;
import dev.zvaryyka.notificationservice.models.NotificationStatuses;
import dev.zvaryyka.notificationservice.repository.NotificationRepository;
import dev.zvaryyka.notificationservice.request.NotificationRequest;
import dev.zvaryyka.notificationservice.response.*;
import jakarta.persistence.EntityNotFoundException;
import org.apache.kafka.common.protocol.types.Field;
import org.hibernate.grammars.hql.HqlParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationService {

    private final GroupMembersService groupMembersService;

    private final TemplateService templateService;

    private final DeviceService deviceService;

    private final RecipientService recipientService;

    private final NotificationKafkaProducer notificationKafkaProducer;

    private final NotificationRepository notificationRepository;

    private final NotificationTypeService notificationTypeService;

    private final NotificationStatusService notificationStatusService;

    @Autowired
    public NotificationService(GroupMembersService groupMembersService, TemplateService templateService, DeviceService deviceService, RecipientService recipientService, NotificationKafkaProducer notificationKafkaProducer, NotificationRepository notificationRepository, NotificationTypeService notificationTypeService, NotificationStatusService notificationStatusService) {
        this.groupMembersService = groupMembersService;
        this.templateService = templateService;
        this.deviceService = deviceService;
        this.recipientService = recipientService;
        this.notificationKafkaProducer = notificationKafkaProducer;
        this.notificationRepository = notificationRepository;
        this.notificationTypeService = notificationTypeService;
        this.notificationStatusService = notificationStatusService;
    }

    public String sendMessagesToGroupRecipients(NotificationRequest notificationRequest, UserInfo userInfo, TokenResponse tokenResponse) {
        String token = "Bearer " + tokenResponse.getToken();

        int recipientCount = 0;

        int deviceCount = 0;


        List<RecipientGroupMemberResponse> recipientGroupMemberResponse = groupMembersService.getMembersByGroupId(notificationRequest.getGroupId(), token);
        MessageTemplateResponse templateResponse = templateService.getMessageTemplateByTemplateId(notificationRequest.getTemplateId(), token);

        for (RecipientGroupMemberResponse recipientGroupMember : recipientGroupMemberResponse) {
            UUID uuid = recipientGroupMember.getRecipientId();
            RecipientResponse recipient = recipientService.getRecipientByRecipientId(uuid, token);
            System.out.println(recipient.getEmail());
            // Отправка Email
            if (recipient.getEmail() != null) {
                Notification notification = new Notification(
                        notificationTypeService.findByTypeName("Email"),
                        notificationStatusService.findByStatusName(NotificationStatuses.CREATED.toString()
                        ), OffsetDateTime.now(), recipient.getEmail(), templateResponse.getSubject(), templateResponse.getBody()
                );
                notificationRepository.save(notification);
                EmailNotificationDTO emailNotification = new EmailNotificationDTO();
                emailNotification.setNotificationId(String.valueOf(notification.getId()));
                emailNotification.setEmail(recipient.getEmail());
                emailNotification.setSubject(templateResponse.getSubject());
                emailNotification.setBody(templateResponse.getBody());

                notificationKafkaProducer.sendEmailNotification(emailNotification);
            }
            // Отправка SMS
            if (recipient.getPhoneNumber() != null) {
                Notification notification = new Notification(
                        notificationTypeService.findByTypeName("Phone"),
                        notificationStatusService.findByStatusName(NotificationStatuses.CREATED.toString()
                        ), OffsetDateTime.now(), recipient.getPhoneNumber(), templateResponse.getSubject(), templateResponse.getBody()
                );
                notificationRepository.save(notification);
                SmsNotificationDTO smsNotification = new SmsNotificationDTO();
                smsNotification.setNotificationId(String.valueOf(notification.getId()));
                smsNotification.setPhoneNumber(recipient.getPhoneNumber());
                smsNotification.setMessage(templateResponse.getBody());

                notificationKafkaProducer.sendSmsNotification(smsNotification);
            }

            recipientCount++;

            List<DeviceResponse> deviceResponses = deviceService.getDevicesByRecipientId(uuid, token);

            for (DeviceResponse deviceResponse : deviceResponses) {
                String deviceType = deviceResponse.getDeviceType();

                Notification notification = new Notification(
                        notificationTypeService.findByTypeName(deviceType),
                        notificationStatusService.findByStatusName(NotificationStatuses.CREATED.toString()
                        ), OffsetDateTime.now(), deviceResponse.getDeviceToken(), templateResponse.getSubject(), templateResponse.getBody()
                );
                notificationRepository.save(notification);
                DeviceNotificationDTO deviceNotification = new DeviceNotificationDTO();
                deviceNotification.setNotificationId(String.valueOf(notification.getId()));
                deviceNotification.setDeviceToken(deviceResponse.getDeviceToken());
                deviceNotification.setDeviceType(deviceType);
                deviceNotification.setMessageBody(templateResponse.getBody());
                notificationKafkaProducer.sendDeviceNotification(deviceNotification);
                deviceCount++;
            }
        }

        return "Sent " + recipientCount + " messages to email and " + deviceCount + " messages to devices";

    }
    public void changeStatusToSent(NotificationIdDTO notificationIdDTO) {
        System.out.println("Id для поиска в базе данных" + notificationIdDTO.getNotificationId());
        Optional<Notification> notificationOptional = notificationRepository.findById(UUID.fromString(notificationIdDTO.getNotificationId()));
        if (notificationOptional.isPresent()) {
            Notification notification = notificationOptional.get();
            notification.setStatus(notificationStatusService.findByStatusName(NotificationStatuses.SENT.toString()));
            notificationRepository.save(notification);
        } else {
            throw new EntityNotFoundException("Notification with ID " + notificationIdDTO.getNotificationId() + " not found.");
        }
    }



}

