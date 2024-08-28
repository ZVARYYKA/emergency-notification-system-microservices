package dev.zvaryyka.notificationservice.repository;


import dev.zvaryyka.notificationservice.models.NotificationType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NotificationTypeRepository extends JpaRepository<NotificationType,Integer> {

    NotificationType findByTypeName(String typeName);
}
