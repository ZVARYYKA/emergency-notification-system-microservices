package dev.zvaryyka.rebalancerservice.dto

data class DeviceNotificationDTO(

    var notificationId: String,
    var deviceToken: String,
    var deviceType: String,
    var messageBody: String,

)

