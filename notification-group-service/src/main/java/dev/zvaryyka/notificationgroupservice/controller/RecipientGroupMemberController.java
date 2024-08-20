package dev.zvaryyka.notificationgroupservice.controller;


import dev.zvaryyka.notificationgroupservice.dto.RecipientGroupMemberDTO;
import dev.zvaryyka.notificationgroupservice.model.RecipientGroupMember;
import dev.zvaryyka.notificationgroupservice.model.UserInfo;
import dev.zvaryyka.notificationgroupservice.response.TokenResponse;
import dev.zvaryyka.notificationgroupservice.service.RecipientGroupMemberService;
import dev.zvaryyka.notificationgroupservice.service.UserService;
import org.apache.http.protocol.HTTP;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/members")
public class RecipientGroupMemberController {

    private final RecipientGroupMemberService recipientGroupMemberService;
    private final UserService userService;


    @Autowired
    public RecipientGroupMemberController(RecipientGroupMemberService recipientGroupMemberService, UserService userService) {
        this.recipientGroupMemberService = recipientGroupMemberService;
        this.userService = userService;
    }
    @GetMapping("/getAllByGroupId/{groupId}")
    public ResponseEntity<List<RecipientGroupMember>> getAllByGroupId(@PathVariable UUID groupId) {
        UserInfo userInfo = userService.getUserInfoByToken();

        return new ResponseEntity<>(recipientGroupMemberService.getAllByGroupId(groupId,userInfo),HttpStatus.OK);

    }

    @GetMapping("/getOneById/{id}")
    public ResponseEntity<RecipientGroupMember> getById(@PathVariable("id") UUID uuid) {
        return new ResponseEntity<>(recipientGroupMemberService.getOneById(uuid),HttpStatus.OK);
    }

    @PostMapping("/add")
    public ResponseEntity<RecipientGroupMember> addNewMember(
            @RequestBody RecipientGroupMemberDTO recipientGroupMemberDTO) {


        UserInfo userInfo = userService.getUserInfoByToken();
        TokenResponse tokenResponse = userService.getUserToken();

        return new ResponseEntity<>(recipientGroupMemberService.addNewMember(
                recipientGroupMemberDTO,
                userInfo,
                tokenResponse),
                HttpStatus.OK);

    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<RecipientGroupMember> deleteMember(@PathVariable UUID id) {
        return new ResponseEntity<>(recipientGroupMemberService.deleteMember(id),HttpStatus.OK);
    }


}
