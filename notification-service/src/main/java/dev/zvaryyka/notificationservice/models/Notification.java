package dev.zvaryyka.notificationservice.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@Entity
@Table(name = "notifications")
@NoArgsConstructor
public class Notification {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @ColumnDefault("uuid_generate_v4()")
    @Column(name = "id", nullable = false)
    private UUID id;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "type_id", nullable = false)
    private NotificationType type;

    @NotNull
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "status_id", nullable = false)
    private NotificationStatus status;

    @NotNull
    @ColumnDefault("now()")
    @Column(name = "sent_at", nullable = false)
    private OffsetDateTime sentAt;

    @Size(max = 255)
    @NotNull
    @Column(name = "destination", nullable = false)
    private String destination;

    @Size(max = 255)
    @Column(name = "subject")
    private String subject;

    @NotNull
    @Column(name = "message_body", nullable = false, length = Integer.MAX_VALUE)
    private String messageBody;


    public Notification(
            NotificationType type,
            NotificationStatus status,
            OffsetDateTime sentAt,
            String destination,
            String subject,
            String messageBody
          ) {
        this.type = type;
        this.status = status;
        this.sentAt = sentAt;
        this.destination = destination;
        this.subject = subject;
        this.messageBody = messageBody;
    }
}