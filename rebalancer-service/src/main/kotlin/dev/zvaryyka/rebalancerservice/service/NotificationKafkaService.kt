package dev.zvaryyka.notificationservice.service


import dev.zvaryyka.rebalancerservice.dto.DeviceNotificationDTO
import dev.zvaryyka.rebalancerservice.dto.EmailNotificationDTO
import dev.zvaryyka.rebalancerservice.dto.SmsNotificationDTO
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.stereotype.Service

@Service
class NotificationKafkaService(
    private val deviceKafkaTemplate: KafkaTemplate<String, DeviceNotificationDTO>,
    private val emailKafkaTemplate: KafkaTemplate<String, EmailNotificationDTO>,
    private val smsKafkaTemplate: KafkaTemplate<String, SmsNotificationDTO>
) {

    fun sendDeviceNotification(notification: DeviceNotificationDTO) {
        try {
            deviceKafkaTemplate.send("device-notifications", notification)
        } catch (e: Exception) {
            println("Failed to send device notification: ${e.message}")
        }
    }

    fun sendEmailNotification(notification: EmailNotificationDTO) {
        try {
            emailKafkaTemplate.send("email-notifications", notification)
        } catch (e: Exception) {
            println("Failed to send email notification: ${e.message}")
        }
    }

    fun sendSmsNotification(notification: SmsNotificationDTO) {
        try {
            smsKafkaTemplate.send("sms-notifications", notification)
        } catch (e: Exception) {
            println("Failed to send SMS notification: ${e.message}")
        }
    }
}
