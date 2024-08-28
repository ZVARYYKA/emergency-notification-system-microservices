package dev.zvaryyka.notificationservice.repository;

import dev.zvaryyka.notificationservice.models.NotificationStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationStatusRepository extends JpaRepository<NotificationStatus,Integer> {

    NotificationStatus findByStatusName(String statusName);
}
