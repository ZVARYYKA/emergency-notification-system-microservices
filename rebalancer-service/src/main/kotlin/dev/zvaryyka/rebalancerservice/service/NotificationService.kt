package dev.zvaryyka.rebalancerservice.service

import dev.zvaryyka.notificationservice.service.NotificationKafkaService
import dev.zvaryyka.rebalancerservice.dto.DeviceNotificationDTO
import dev.zvaryyka.rebalancerservice.dto.EmailNotificationDTO
import dev.zvaryyka.rebalancerservice.dto.SmsNotificationDTO
import dev.zvaryyka.rebalancerservice.models.Notification
import dev.zvaryyka.rebalancerservice.models.NotificationStatuses
import dev.zvaryyka.rebalancerservice.repository.NotificationRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Service
import java.time.OffsetDateTime
import java.time.temporal.ChronoUnit

@Service
class NotificationService(
    @Autowired
    private val notificationRepository: NotificationRepository,
    private val notificationStatusService: NotificationStatusService,
    private val notificationKafkaService: NotificationKafkaService

) {


    @Scheduled(fixedRate = 300000)
    fun checkUnSendNotificationAndRewriteInKafka() {
        val fiveMinutesAgo = OffsetDateTime.now().minus(5, ChronoUnit.MINUTES)
        val failedNotifications: List<Notification> =
            notificationRepository.findOldCreatedOrFailedNotifications(fiveMinutesAgo);
        for (notification in failedNotifications) {
            notification.status = notificationStatusService.findByStatusName(NotificationStatuses.CREATED.toString());
            val type: String = notification.type?.typeName.toString();
            //switch (Android,Phone,IPhone,Email)
            when (type) {
                "Android" -> {
                    val notificationForKafka = DeviceNotificationDTO(
                        notification.id.toString(),
                        notification.destination.toString(),
                        type,
                        notification.messageBody.toString()
                    )

                    notificationKafkaService.sendDeviceNotification(notificationForKafka)
                }

                "Phone" -> {
                    val notificationForKafka = SmsNotificationDTO(
                        notification.id.toString(),
                        notification.destination.toString(),
                        notification.messageBody.toString()
                    )
                    notificationKafkaService.sendSmsNotification(notificationForKafka)
                }

                "IPhone" -> {
                    val notificationForKafka = DeviceNotificationDTO(
                        notification.id.toString(),
                        notification.destination.toString(),
                        type,
                        notification.messageBody.toString()
                    )
                    notificationKafkaService.sendDeviceNotification(notificationForKafka)
                }

                "Email" -> {
                    val notificationForKafka = EmailNotificationDTO(
                        notification.id.toString(),
                        notification.destination.toString(),
                        notification.subject.toString(),
                        notification.messageBody.toString()
                    )
                    notificationKafkaService.sendEmailNotification(notificationForKafka)
                }

            }
            println("Сообщение отправлено в Kafka, $type")
            notificationRepository.save(notification);
        }

    }
}