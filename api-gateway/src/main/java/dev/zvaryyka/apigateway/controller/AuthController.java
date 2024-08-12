package dev.zvaryyka.apigateway.controller;

import dev.zvaryyka.apigateway.request.AuthRequest;
import dev.zvaryyka.apigateway.request.RefreshTokenRequest;
import dev.zvaryyka.apigateway.response.TokenResponse;
import dev.zvaryyka.apigateway.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final TokenService tokenService;

    @Autowired
    public AuthController(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    @PostMapping("/login")
    public ResponseEntity<TokenResponse> login(@RequestBody AuthRequest authRequest) {
        TokenResponse tokenResponse =
                tokenService.getTokenFromServer(authRequest.getUsername(), authRequest.getPassword());
        return ResponseEntity.ok(tokenResponse);
    }

    @PostMapping("/refreshToken")
    public ResponseEntity<TokenResponse> refreshToken(@RequestBody RefreshTokenRequest request) {
        TokenResponse tokenResponse = tokenService.refreshToken(request.getRefreshToken());
        return ResponseEntity.ok(tokenResponse);
    }

}
