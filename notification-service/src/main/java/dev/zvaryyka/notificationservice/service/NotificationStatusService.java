package dev.zvaryyka.notificationservice.service;

import dev.zvaryyka.notificationservice.models.NotificationStatus;
import dev.zvaryyka.notificationservice.repository.NotificationStatusRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class NotificationStatusService {

    private final NotificationStatusRepository notificationStatusRepository;

    @Autowired
    public NotificationStatusService(NotificationStatusRepository notificationStatusRepository) {
        this.notificationStatusRepository = notificationStatusRepository;
    }
    public NotificationStatus findByStatusName(String statusName) {
        return notificationStatusRepository.findByStatusName(statusName);
    }
}
