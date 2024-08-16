package dev.zvaryyka.templateservice.models;

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