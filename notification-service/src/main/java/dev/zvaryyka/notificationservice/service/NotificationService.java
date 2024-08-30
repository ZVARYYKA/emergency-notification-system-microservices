package dev.zvaryyka.notificationservice.service;

import dev.zvaryyka.notificationservice.dto.DeviceNotificationDTO;
import dev.zvaryyka.notificationservice.dto.EmailNotificationDTO;
import dev.zvaryyka.notificationservice.dto.NotificationIdDTO;
import dev.zvaryyka.notificationservice.dto.SmsNotificationDTO;
import dev.zvaryyka.notificationservice.exception.CustomException;
import dev.zvaryyka.notificationservice.models.Notification;
import dev.zvaryyka.notificationservice.models.NotificationStatuses;
import dev.zvaryyka.notificationservice.repository.NotificationRepository;
import dev.zvaryyka.notificationservice.request.NotificationRequest;
import dev.zvaryyka.notificationservice.response.*;
import jakarta.persistence.EntityNotFoundException;
import org.apache.kafka.common.protocol.types.Field;
import org.hibernate.grammars.hql.HqlParser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.OffsetDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class NotificationService {

    private static final Logger logger = LoggerFactory.getLogger(NotificationService.class);

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

        try {
            List<RecipientGroupMemberResponse> recipientGroupMembers = groupMembersService.getMembersByGroupId(notificationRequest.getGroupId(), token);
            MessageTemplateResponse templateResponse = templateService.getMessageTemplateByTemplateId(notificationRequest.getTemplateId(), token);

            for (RecipientGroupMemberResponse member : recipientGroupMembers) {
                RecipientResponse recipient = recipientService.getRecipientByRecipientId(member.getRecipientId(), token);

                if (recipient.getEmail() != null) {
                    sendEmailNotification(recipient, templateResponse);
                    recipientCount++;
                }

                if (recipient.getPhoneNumber() != null) {
                    sendSmsNotification(recipient, templateResponse);
                }

                List<DeviceResponse> deviceResponses = deviceService.getDevicesByRecipientId(member.getRecipientId(), token);
                for (DeviceResponse device : deviceResponses) {
                    sendDeviceNotification(device, templateResponse);
                    deviceCount++;
                }
            }

            return String.format("Sent %d messages to email and phone, and %d messages to devices", recipientCount, deviceCount);

        } catch (Exception e) {
            logger.error("Error sending notifications: ", e);
            throw new CustomException("Failed to send notifications", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    private void sendEmailNotification(RecipientResponse recipient, MessageTemplateResponse templateResponse) {
        Notification notification = createNotification(recipient.getEmail(), "Email", templateResponse);
        notificationRepository.save(notification);

        EmailNotificationDTO emailNotification = new EmailNotificationDTO();
        emailNotification.setNotificationId(String.valueOf(notification.getId()));
        emailNotification.setEmail(recipient.getEmail());
        emailNotification.setSubject(templateResponse.getSubject());
        emailNotification.setBody(templateResponse.getBody());

        notificationKafkaProducer.sendEmailNotification(emailNotification);
    }

    private void sendSmsNotification(RecipientResponse recipient, MessageTemplateResponse templateResponse) {
        Notification notification = createNotification(recipient.getPhoneNumber(), "Phone", templateResponse);
        notificationRepository.save(notification);

        SmsNotificationDTO smsNotification = new SmsNotificationDTO();
        smsNotification.setNotificationId(String.valueOf(notification.getId()));
        smsNotification.setPhoneNumber(recipient.getPhoneNumber());
        smsNotification.setMessage(templateResponse.getBody());

        notificationKafkaProducer.sendSmsNotification(smsNotification);
    }

    private void sendDeviceNotification(DeviceResponse deviceResponse, MessageTemplateResponse templateResponse) {
        Notification notification = createNotification(deviceResponse.getDeviceToken(), deviceResponse.getDeviceType(), templateResponse);
        notificationRepository.save(notification);

        DeviceNotificationDTO deviceNotification = new DeviceNotificationDTO();
        deviceNotification.setNotificationId(String.valueOf(notification.getId()));
        deviceNotification.setDeviceToken(deviceResponse.getDeviceToken());
        deviceNotification.setDeviceType(deviceResponse.getDeviceType());
        deviceNotification.setMessageBody(templateResponse.getBody());

        notificationKafkaProducer.sendDeviceNotification(deviceNotification);
    }

    private Notification createNotification(String recipient, String type, MessageTemplateResponse templateResponse) {
        return new Notification(
                notificationTypeService.findByTypeName(type),
                notificationStatusService.findByStatusName(NotificationStatuses.CREATED.toString()),
                OffsetDateTime.now(),
                recipient,
                templateResponse.getSubject(),
                templateResponse.getBody()
        );
    }

    public void changeStatusToSent(NotificationIdDTO notificationIdDTO) {
        logger.info("Changing status to SENT for notification ID: {}", notificationIdDTO.getNotificationId());

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


