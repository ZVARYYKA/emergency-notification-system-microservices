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
@Table(name = "notification_types")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
 class NotificationType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @ColumnDefault("nextval('notification_types_id_seq'::regclass)")
    @Column(name = "id", nullable = false)
     var id: Int? = null

    @Size(max = 50)
    @NotNull
    @Column(name = "type_name", nullable = false, length = 50)
     var typeName: String? = null
}