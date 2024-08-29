package dev.zvaryyka.rebalancerservice.repository

import dev.zvaryyka.rebalancerservice.models.NotificationStatus
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface NotificationStatusRepository : JpaRepository<NotificationStatus, Int> {
    fun findByStatusName(statusName: String) : NotificationStatus
}