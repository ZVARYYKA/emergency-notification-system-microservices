package dev.zvaryyka.notificationservice.response;

import lombok.Getter;
import lombok.Setter;

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