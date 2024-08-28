package dev.zvaryyka.notificationservice.response;

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

