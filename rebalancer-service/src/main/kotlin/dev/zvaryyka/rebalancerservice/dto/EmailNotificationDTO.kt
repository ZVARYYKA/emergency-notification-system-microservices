package dev.zvaryyka.rebalancerservice.dto

data class EmailNotificationDTO(

    var notificationId: String,
    var email: String,
    var subject: String,
    var body: String,
)
