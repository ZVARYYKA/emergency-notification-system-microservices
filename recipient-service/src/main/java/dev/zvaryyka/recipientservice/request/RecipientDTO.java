package dev.zvaryyka.recipientservice.request;

import jakarta.persistence.Column;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RecipientDTO {

    @Size(max = 255)
    @NotBlank(message = "Keycloak user id is a required field")
    private String keycloakUserId;

    @Size(max = 255)
    @NotBlank(message = "Name is a required field")
    private String name;

    @Size(max = 255,min = 2)
    @NotBlank(message = "Email is a required field and must be at least 2 characters long")
    private String email;

    @Size(max = 20)
    @NotBlank(message = "Phone number is a required field")
    private String phoneNumber;
}
