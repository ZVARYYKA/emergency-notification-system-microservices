package dev.zvaryyka.apigateway.service;


import dev.zvaryyka.apigateway.feignclient.TokenClient;
import dev.zvaryyka.apigateway.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Service
public class TokenService {

    @Value("${keycloak.clientId}")
    private String clientId;
    @Value("${keycloak.clientSecret}")
    private String clientSecret;
    @Value("${keycloak.tokenUrl}")
    private String tokenUrl;

    private final TokenClient tokenClient;

    @Autowired
    public TokenService(TokenClient tokenClient) {
        this.tokenClient = tokenClient;
    }

    public TokenResponse refreshToken(String refreshToken) {
        return tokenClient.refreshToken(
                formMultiMap(refreshToken)
        );
    }

    public TokenResponse getTokenFromServer(String username, String password) {
        return tokenClient.refreshToken(
                formMultiMap(username, password)
        );
    }

    private MultiValueMap<String, String> formMultiMap(String username, String password) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("grant_type", "password");
        formData.add("username", username);
        formData.add("password", password);

        return formData;
    }

    private MultiValueMap<String, String> formMultiMap(String refreshToken) {
        MultiValueMap<String, String> formData = new LinkedMultiValueMap<>();
        formData.add("client_id", clientId);
        formData.add("client_secret", clientSecret);
        formData.add("grant_type", "refresh_token");
        formData.add("refresh_token", refreshToken);

        return formData;
    }

}
