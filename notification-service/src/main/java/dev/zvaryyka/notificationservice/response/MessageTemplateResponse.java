package dev.zvaryyka.notificationservice.response;

import jakarta.persistence.Column;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.ColumnDefault;

import java.time.OffsetDateTime;
import java.util.UUID;

@Getter
@Setter
@ToString
public class MessageTemplateResponse {

    private UUID id;


    private UUID keycloakUserId;


    private String subject;


    private String body;


    private OffsetDateTime createdAt;
}
