package dev.zvaryyka.apigateway.service;

import dev.zvaryyka.apigateway.exception.CustomException;
import dev.zvaryyka.apigateway.feignclient.TokenClient;
import dev.zvaryyka.apigateway.response.TokenResponse;
import feign.FeignException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

@Service
public class TokenService {

    @Value("${keycloak.clientId}")
    private String clientId;
    @Value("${keycloak.clientSecret}")
    private String clientSecret;

    private final TokenClient tokenClient;

    @Autowired
    public TokenService(TokenClient tokenClient) {
        this.tokenClient = tokenClient;
    }

    public TokenResponse refreshToken(String refreshToken) {
        try {
            return tokenClient.refreshToken(
                    formMultiMap(refreshToken)
            );
        } catch (FeignException.Unauthorized e) {
            throw new CustomException("Invalid refresh token or unauthorized request", HttpStatus.UNAUTHORIZED);
        } catch (FeignException e) {
            throw new CustomException("An error occurred while refreshing the token", HttpStatus.BAD_REQUEST);
        }
    }

    public TokenResponse getTokenFromServer(String username, String password) {
        try {
            return tokenClient.refreshToken(
                    formMultiMap(username, password)
            );
        } catch (FeignException.Unauthorized e) {
            throw new CustomException("Invalid username or password", HttpStatus.UNAUTHORIZED);
        } catch (FeignException e) {
            throw new CustomException("An error occurred while retrieving the token", HttpStatus.BAD_REQUEST);
        }
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
