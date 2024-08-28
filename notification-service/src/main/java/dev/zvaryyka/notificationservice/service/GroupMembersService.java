package dev.zvaryyka.notificationservice.service;

import dev.zvaryyka.notificationservice.feignclient.GroupMembersClient;
import dev.zvaryyka.notificationservice.response.RecipientGroupMemberResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class GroupMembersService {
    private final GroupMembersClient groupMembersClient;

    @Autowired
    public GroupMembersService(GroupMembersClient groupMembersClient) {
        this.groupMembersClient = groupMembersClient;
    }

    public List<RecipientGroupMemberResponse> getMembersByGroupId(UUID groupId, String token) {

        return groupMembersClient.getAllByGroupId(token, groupId );

    }
}
