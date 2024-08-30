package dev.zvaryyka.apigateway.service;

import dev.zvaryyka.apigateway.request.RegisterUserRequest;
import dev.zvaryyka.apigateway.response.RegistrationResponse;
import jakarta.ws.rs.core.Response;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Collections;

@Service
public class RegistrationService {

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.admin.realm}")
    private String adminRealm;

    @Value("${keycloak.admin.clientId}")
    private String adminClientId;

    @Value("${keycloak.clientSecret}")
    private String clientSecret;

    @Value("${keycloak.adminUsername}")
    private String adminUsername;

    @Value("${keycloak.adminPassword}")
    private String adminPassword;



    private Keycloak getAdminKeycloakInstance() {
        return KeycloakBuilder.builder()
                .serverUrl("http://localhost:8080")
                .realm(adminRealm)
                .clientId(adminClientId)
                .clientSecret(clientSecret)
                .username(adminUsername)
                .password(adminPassword)
                .grantType("password")
                .build();
    }

    public RegistrationResponse registerNewUser(RegisterUserRequest userRequest) {
        Keycloak keycloak = getAdminKeycloakInstance();

        UserRepresentation user = new UserRepresentation();
        user.setUsername(userRequest.getUsername());
        user.setEmail(userRequest.getEmail());
        user.setFirstName(userRequest.getFirstName());
        user.setLastName(userRequest.getLastName());
        user.setEnabled(true);

        CredentialRepresentation credential = new CredentialRepresentation();
        credential.setTemporary(false);
        credential.setType(CredentialRepresentation.PASSWORD);
        credential.setValue(userRequest.getPassword());
        user.setCredentials(Collections.singletonList(credential));

        Response response = keycloak.realm(realm).users().create(user);

        String responseBody;
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(response.readEntity(InputStream.class)))) {
            responseBody = reader.lines().reduce("", (acc, line) -> acc + line + "\n");
        } catch (Exception e) {
            responseBody = "Error reading response body";
        }

        keycloak.close();

        return new RegistrationResponse(response.getStatus(), responseBody);
    }
}
