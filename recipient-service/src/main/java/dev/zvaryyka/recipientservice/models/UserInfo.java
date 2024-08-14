package dev.zvaryyka.recipientservice.models;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class UserInfo {

    private String sub;

    private String username;

    private String email;

    private String name;

    private String familyName;
}

//sub: a16853cb-cfdc-4b8a-ada4-8b30d1788dea
//resource_access: {account={roles=[manage-account, manage-account-links, view-profile]}}
//email_verified: false
//allowed-origins: [http://localhost:8765]
//iss: http://localhost:8080/realms/SpringBootKeycloak
//typ: Bearer
//preferred_username: johndoe
//given_name: John
//sid: 48efd0f7-1b07-4e21-8933-9bd1b5517db0
//aud: [account]
//acr: 1
//realm_access: {roles=[default-roles-springbootkeycloak, offline_access, uma_authorization]}
//azp: login-app
//scope: roles email profile
//name: John Doe
//exp: 2024-08-14T10:48:38Z
//iat: 2024-08-14T10:43:38Z
//family_name: Doe
//jti: 97e13979-4d82-4882-8b35-5d5f6df6e795
//email: johndoe@example.com
