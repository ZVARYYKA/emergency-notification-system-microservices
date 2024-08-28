package dev.zvaryyka.notificationservice.service;

import dev.zvaryyka.notificationservice.models.NotificationType;
import dev.zvaryyka.notificationservice.repository.NotificationTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationTypeService {
    private final NotificationTypeRepository notificationTypeRepository;

    @Autowired
    public NotificationTypeService(NotificationTypeRepository notificationTypeRepository) {
        this.notificationTypeRepository = notificationTypeRepository;
    }
    public NotificationType findByTypeName(String typeName) {
        return notificationTypeRepository.findByTypeName(typeName);
    }
}
