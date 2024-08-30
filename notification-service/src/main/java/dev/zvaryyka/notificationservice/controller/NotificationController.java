package dev.zvaryyka.notificationservice.controller;

import dev.zvaryyka.notificationservice.request.NotificationRequest;
import dev.zvaryyka.notificationservice.response.TokenResponse;
import dev.zvaryyka.notificationservice.response.UserInfo;
import dev.zvaryyka.notificationservice.service.NotificationService;
import dev.zvaryyka.notificationservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notifications")
public class NotificationController {

    private final NotificationService notificationService;
    private final UserService userService;

    @Autowired
    public NotificationController(NotificationService notificationService, UserService userService) {
        this.notificationService = notificationService;
        this.userService = userService;
    }

    @PostMapping("/sendMessages")
    public ResponseEntity<String> sendMessagesToRecipients(@RequestBody NotificationRequest notificationRequest) {


        UserInfo userInfo = userService.getUserInfoByToken();
        TokenResponse token = userService.getUserToken();
        System.out.println(userInfo.getSub());
        return new ResponseEntity<>(notificationService.sendMessagesToGroupRecipients(notificationRequest,userInfo,token), HttpStatus.OK);


    }
}
