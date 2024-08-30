package dev.zvaryyka.apigateway.response;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegistrationResponse {

    private final int status;
    private final String body;

    public RegistrationResponse(int status, String body) {
        this.status = status;
        this.body = body;
    }
}