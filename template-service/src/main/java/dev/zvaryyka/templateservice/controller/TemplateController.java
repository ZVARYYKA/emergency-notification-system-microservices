package dev.zvaryyka.templateservice.controller;

import dev.zvaryyka.templateservice.dto.MessageTemplateDTO;
import dev.zvaryyka.templateservice.models.MessageTemplate;
import dev.zvaryyka.templateservice.models.UserInfo;
import dev.zvaryyka.templateservice.service.TemplateService;
import dev.zvaryyka.templateservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/templates")
public class TemplateController {

    private final TemplateService templateService;
    private final UserService userService;

    @Autowired
    public TemplateController(TemplateService templateService, UserService userService) {
        this.templateService = templateService;
        this.userService = userService;
    }

    @GetMapping("/getAllByUserId")
    public ResponseEntity<List<MessageTemplate>> getAllMessageTemplatesByUserId() {
        UserInfo userInfo = userService.getUserInfoByToken();
        return ResponseEntity.ok(templateService.getAllMessageTemplatesByUserId(userInfo));
    }

    @GetMapping("/getById/{id}")
    public ResponseEntity<MessageTemplate> getMessageTemplateByTemplateId( @PathVariable UUID id) {

        UserInfo userInfo = userService.getUserInfoByToken();
        return ResponseEntity.ok(templateService.getMessageTemplateByTemplateId(id,userInfo));

    }
    @PostMapping("/add")
    public ResponseEntity<MessageTemplate> addNewMessageTemplate(@RequestBody MessageTemplateDTO messageTemplateDTO) {

        UserInfo userInfo = userService.getUserInfoByToken();
        return ResponseEntity.ok(templateService.addNewMessageTemplate(messageTemplateDTO,userInfo));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<MessageTemplate> updateMessageTemplate(@PathVariable UUID id, @RequestBody MessageTemplateDTO messageTemplateDTO) {

        UserInfo userInfo = userService.getUserInfoByToken();
        return ResponseEntity.ok(templateService.updateMessageTemplate(id,messageTemplateDTO,userInfo));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<MessageTemplate> deleteMessageTemplate(@PathVariable UUID id) {
        UserInfo userInfo = userService.getUserInfoByToken();
        return ResponseEntity.ok(templateService.deleteMessageTemplate(id,userInfo));
    }

}
