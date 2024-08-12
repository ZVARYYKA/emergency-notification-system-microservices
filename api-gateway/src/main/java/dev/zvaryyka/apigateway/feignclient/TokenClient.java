package dev.zvaryyka.apigateway.feignclient;

import dev.zvaryyka.apigateway.response.TokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(name = "keycloak", url = "${keycloak.tokenUrl}")
public interface TokenClient {

    @PostMapping
    TokenResponse getTokenFromServer(@RequestBody MultiValueMap<String,String> formData);
    @PostMapping
    TokenResponse refreshToken(@RequestBody MultiValueMap<String,String> formData);

}
