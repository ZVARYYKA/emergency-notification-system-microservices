package dev.zvaryyka.apigateway.controller;

import dev.zvaryyka.apigateway.request.RegisterUserRequest;
import dev.zvaryyka.apigateway.service.RegistrationService;
import jakarta.ws.rs.core.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class RegistrationController {


    private final RegistrationService registrationService;

    @Autowired
    public RegistrationController(RegistrationService registrationService) {
        this.registrationService = registrationService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerNewUser(@RequestBody RegisterUserRequest userRequest) {

        Response response = registrationService.registerNewUser(userRequest);
        if (response.getStatus() == 201) {
            return new ResponseEntity<>("User registered successfully", HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>("Failed to register user: " + response.getStatusInfo().getReasonPhrase(), HttpStatus.valueOf(response.getStatus()));
        }
    }




}
