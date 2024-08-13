package dev.zvaryyka.apigateway.feignclient;

import dev.zvaryyka.apigateway.response.TokenResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@FeignClient(name = "keycloak", url = "${keycloak.url}")
public interface TokenClient {

    @PostMapping("/realms/SpringBootKeycloak/protocol/openid-connect/token")
    TokenResponse getTokenFromServer(@RequestBody MultiValueMap<String,String> formData);

    @PostMapping("/realms/master/protocol/openid-connect/token")
    TokenResponse getTokenForAdminFromServer(@RequestBody MultiValueMap<String,String> formData);

    @PostMapping("/realms/SpringBootKeycloak/protocol/openid-connect/token")
    TokenResponse refreshToken(@RequestBody MultiValueMap<String,String> formData);


}
