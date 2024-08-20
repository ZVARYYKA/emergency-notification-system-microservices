package dev.zvaryyka.notificationgroupservice.controller;

import dev.zvaryyka.notificationgroupservice.dto.RecipientGroupDTO;
import dev.zvaryyka.notificationgroupservice.model.RecipientGroup;
import dev.zvaryyka.notificationgroupservice.model.UserInfo;
import dev.zvaryyka.notificationgroupservice.service.RecipientGroupService;
import dev.zvaryyka.notificationgroupservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/groups")
public class RecipientGroupController {

    private final RecipientGroupService recipientGroupService;
    private final UserService userService;

    @Autowired
    public RecipientGroupController(RecipientGroupService recipientGroupService, UserService userService) {
        this.recipientGroupService = recipientGroupService;
        this.userService = userService;
    }

    @GetMapping("/getAllByUser")
    public ResponseEntity<List<RecipientGroup>> getAllGroupsByUserId() {
        UserInfo userInfo = userService.getUserInfoByToken();

        return ResponseEntity.ok(recipientGroupService.getAllByUser(userInfo));
    }
    @GetMapping("/getOneById/{id}")
    public ResponseEntity<RecipientGroup> getOneById(@PathVariable UUID id) {
        return ResponseEntity.ok(recipientGroupService.getOneById(id));
    }
    @PostMapping("/add")
    public ResponseEntity<RecipientGroup> addNewGroup(@RequestBody RecipientGroupDTO recipientGroup) {
        UserInfo userInfo = userService.getUserInfoByToken();

        return ResponseEntity.ok(recipientGroupService.addNewGroup(recipientGroup,userInfo));

    }
    @PutMapping("/update/{id}")
    public ResponseEntity<RecipientGroup> updateGroup (@PathVariable UUID id, @RequestBody RecipientGroupDTO recipientGroup) {
        UserInfo userInfo = userService.getUserInfoByToken();

        return ResponseEntity.ok(recipientGroupService.updateGroup(id,recipientGroup,userInfo));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<RecipientGroup> deleteGroup (@PathVariable UUID id) {
        UserInfo userInfo = userService.getUserInfoByToken();

        return ResponseEntity.ok(recipientGroupService.deleteGroup(id,userInfo));
    }

}
