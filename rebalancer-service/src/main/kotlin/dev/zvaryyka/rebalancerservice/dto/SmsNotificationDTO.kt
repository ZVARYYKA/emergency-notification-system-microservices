package dev.zvaryyka.rebalancerservice.dto

data class SmsNotificationDTO(

    var notificationId: String,
    var phoneNumber: String,
    var message: String,
)