package dev.zvaryyka.rebalancerservice.service

import dev.zvaryyka.rebalancerservice.models.NotificationStatus
import dev.zvaryyka.rebalancerservice.repository.NotificationStatusRepository
import org.springframework.stereotype.Service

@Service
class NotificationStatusService (
    private val NotificationStatusRepository: NotificationStatusRepository
){


    fun findByStatusName(toString: String): NotificationStatus? {
        return NotificationStatusRepository.findByStatusName(toString)
    }
}