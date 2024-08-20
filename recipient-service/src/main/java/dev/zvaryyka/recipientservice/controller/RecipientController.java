package dev.zvaryyka.recipientservice.controller;

import dev.zvaryyka.recipientservice.models.Recipient;
import dev.zvaryyka.recipientservice.response.UserInfo;
import dev.zvaryyka.recipientservice.service.RecipientService;
import dev.zvaryyka.recipientservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/recipients")
public class RecipientController {

    private final RecipientService recipientService;
    private final UserService userService;

    @Autowired
    public RecipientController(RecipientService recipientService, UserService userService) {
        this.recipientService = recipientService;
        this.userService = userService;
    }
    @PostMapping("/addFromFile")
    public String uploadFileWithRecipients(@RequestParam("file") MultipartFile file) {


        UserInfo userInfo = userService.getUserInfoByToken();
        return recipientService.addNewRecipientFromFile(file,userInfo);
    }
    @GetMapping("/getAll")
    public ResponseEntity<List<Recipient>> getAllRecipientsByUserToken() {
        UserInfo userInfo = userService.getUserInfoByToken();

        return new ResponseEntity<>(recipientService.getAllRecipientsByUser(userInfo), HttpStatus.OK);

    }
    @GetMapping("/getById/{id}")
    public ResponseEntity<Recipient> getRecipientById(@PathVariable UUID id) {

        return new ResponseEntity<>(recipientService.getRecipientById(id), HttpStatus.OK);
    }
    @PostMapping("/add")
    public ResponseEntity<Recipient> addNewRecipient(@RequestBody Recipient recipient) {

        UserInfo userInfo = userService.getUserInfoByToken();
        return new ResponseEntity<>(recipientService.addNewRecipient(recipient, userInfo), HttpStatus.CREATED);
    }
    //TODO Add check what request do need user
    @PutMapping("/update/{id}")
    public ResponseEntity<Recipient> updateRecipient(@PathVariable UUID id, @RequestBody Recipient recipient) {

        return new ResponseEntity<>(recipientService.updateRecipient(id, recipient), HttpStatus.OK);
    }
    //TODO Add check what request do need user
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Recipient> deleteRecipient(@PathVariable UUID id) {
        return new ResponseEntity<>(recipientService.deleteRecipient(id), HttpStatus.OK);
    }

}
