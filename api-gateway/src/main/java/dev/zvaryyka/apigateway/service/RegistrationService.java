package dev.zvaryyka.apigateway.service;

import dev.zvaryyka.apigateway.request.RegisterUserRequest;
import jakarta.ws.rs.core.Response;


import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.idm.CredentialRepresentation;
import org.keycloak.representations.idm.UserRepresentation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;


import java.util.Collections;

@Service
public class RegistrationService {

    @Value("${keycloak.realm}")
    private String realm;

    @Value("${keycloak.clientId}")
    private String clientId;

    @Value("${keycloak.admin.clientId}")
    private String adminClientId;

    @Value("${keycloak.clientSecret}")
    private String clientSecret;

    @Value("${keycloak.adminUsername}")
    private String adminUsername;

    @Value("${keycloak.adminPassword}")
    private String adminPassword;

    //TODO Add username and mail check
    //TODO Add exception handling

    private Keycloak getAdminKeycloakInstance() {
        return KeycloakBuilder.builder()
                .serverUrl("http://localhost:8080")
                .realm("master")
                .clientId(adminClientId)
                .clientSecret(clientSecret)
                .username(adminUsername)
                .password(adminPassword)
                .grantType("password")
                .build();
    }

    public Response registerNewUser(RegisterUserRequest userRequest) {
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

        Response response = keycloak.realm("master").users().create(user);
        System.out.println(response.getLocation());
        keycloak.close();

        return response;
    }
}
