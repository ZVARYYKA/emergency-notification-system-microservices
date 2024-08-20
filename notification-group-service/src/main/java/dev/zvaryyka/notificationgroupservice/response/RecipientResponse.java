package dev.zvaryyka.notificationgroupservice.response;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.annotations.ColumnDefault;
import org.hibernate.annotations.GenericGenerator;

import java.time.Instant;
import java.util.UUID;

@Getter
@Setter
public class RecipientResponse {


    private UUID id;


    private String keycloakUserId;


    private String name;


    private String email;


    private String phoneNumber;


    private Instant createdAt;

    private Instant updatedAt;


}
