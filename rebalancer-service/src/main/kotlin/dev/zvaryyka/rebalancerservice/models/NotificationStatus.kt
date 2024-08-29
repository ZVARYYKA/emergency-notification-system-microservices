package dev.zvaryyka.rebalancerservice.models

import jakarta.persistence.*
import jakarta.validation.constraints.NotNull
import jakarta.validation.constraints.Size
import lombok.AllArgsConstructor
import lombok.Getter
import lombok.NoArgsConstructor
import lombok.Setter
import org.hibernate.annotations.ColumnDefault

@Entity
@Table(name = "notification_statuses")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
open class NotificationStatus {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('notification_statuses_id_seq'::regclass)")
    @Column(name = "id", nullable = false)
     var id: Int? = null

    @Size(max = 50)
    @NotNull
    @Column(name = "status_name", nullable = false, length = 50)
     var statusName: String? = null
}