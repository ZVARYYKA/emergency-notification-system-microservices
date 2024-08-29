package dev.zvaryyka.rebalancerservice.models

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import org.hibernate.annotations.ColumnDefault
import java.time.OffsetDateTime
import java.util.*

@Entity
@Table(name = "notifications")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
  class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @ColumnDefault("uuid_generate_v4()")
    @Column(name = "id", nullable = false)
     var id: UUID? = null

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "type_id", nullable = false)
     var type: NotificationType? = null

    @NotNull
    @ManyToOne(fetch = FetchType.EAGER, optional = false)
    @JoinColumn(name = "status_id", nullable = false)
     var status: NotificationStatus? = null

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "sent_at", nullable = false)
     var sentAt: OffsetDateTime? = null

    @Size(max = 255)
    @NotNull
    @Column(name = "destination", nullable = false)
     var destination: String? = null

    @Size(max = 255)
    @Column(name = "subject")
     var subject: String? = null

    @NotNull
    @Column(name = "message_body", nullable = false, length = Integer.MAX_VALUE)
     var messageBody: String? = null
}