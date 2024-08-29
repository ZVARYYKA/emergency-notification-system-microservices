package dev.zvaryyka.rebalancerservice.repository

import dev.zvaryyka.rebalancerservice.models.Notification
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.OffsetDateTime

@Repository
interface NotificationRepository : JpaRepository<Notification, String> {

    @Query("SELECT n FROM Notification n WHERE " +
            "(n.status.statusName = 'CREATED' AND n.sentAt < :timeThreshold) OR " +
            "n.status.statusName = 'FAILED'")
    fun findOldCreatedOrFailedNotifications(@Param("timeThreshold") timeThreshold: OffsetDateTime): List<Notification>
}