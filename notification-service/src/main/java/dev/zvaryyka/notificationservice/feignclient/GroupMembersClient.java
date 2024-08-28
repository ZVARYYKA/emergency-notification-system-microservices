package dev.zvaryyka.notificationservice.feignclient;

import dev.zvaryyka.notificationservice.response.RecipientGroupMemberResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "group-members-service", url = "http://localhost:8765/members")
public interface GroupMembersClient {

    @GetMapping("/getAllByGroupId/{groupId}")
    List<RecipientGroupMemberResponse> getAllByGroupId(@RequestHeader("Authorization") String token,
                                                       @PathVariable("groupId") UUID groupId);
}