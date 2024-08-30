package dev.zvaryyka.notificationservice.service;

import dev.zvaryyka.notificationservice.exception.CustomException;
import dev.zvaryyka.notificationservice.feignclient.GroupMembersClient;
import dev.zvaryyka.notificationservice.response.RecipientGroupMemberResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        try {
            List<RecipientGroupMemberResponse> members = groupMembersClient.getAllByGroupId(token, groupId);

            if (members == null || members.isEmpty()) {
                throw new CustomException("No members found for group ID " + groupId, HttpStatus.NOT_FOUND);
            }

            return members;
        } catch (Exception ex) {
            throw new CustomException("Failed to fetch members for group ID " + groupId + ": " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
