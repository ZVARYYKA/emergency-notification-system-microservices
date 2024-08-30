package dev.zvaryyka.notificationgroupservice.service;

import dev.zvaryyka.notificationgroupservice.dto.RecipientGroupMemberDTO;
import dev.zvaryyka.notificationgroupservice.feignclient.RecipientClient;
import dev.zvaryyka.notificationgroupservice.model.RecipientGroup;
import dev.zvaryyka.notificationgroupservice.model.RecipientGroupMember;
import dev.zvaryyka.notificationgroupservice.model.UserInfo;
import dev.zvaryyka.notificationgroupservice.repository.RecipientGroupMemberRepository;
import dev.zvaryyka.notificationgroupservice.response.RecipientResponse;
import dev.zvaryyka.notificationgroupservice.response.TokenResponse;
import dev.zvaryyka.notificationgroupservice.exception.CustomException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecipientGroupMemberService {

    private final RecipientGroupMemberRepository recipientGroupMemberRepository;
    private final RecipientGroupService recipientGroupService;
    private final RecipientService recipientService;
    private final UserService userService;

    @Autowired
    public RecipientGroupMemberService(
            RecipientGroupMemberRepository recipientGroupMemberRepository,
            RecipientGroupService recipientGroupService,
            RecipientService recipientService,
            UserService userService) {
        this.recipientGroupMemberRepository = recipientGroupMemberRepository;
        this.recipientGroupService = recipientGroupService;
        this.recipientService = recipientService;
        this.userService = userService;
    }

    public List<RecipientGroupMember> getAllByGroupId(UUID groupId, UserInfo userInfo) {
        // Check if the user has access to the group
        validateUserAccessToGroup(groupId, userInfo);

        return recipientGroupMemberRepository.findByGroupId(groupId);
    }

    public RecipientGroupMember getOneById(UUID id, UserInfo userInfo) {
        RecipientGroupMember recipientGroupMember = recipientGroupMemberRepository.findById(id)
                .orElseThrow(() -> new CustomException("Recipient Group Member not found", HttpStatus.NOT_FOUND));

        // Check if the user has access to the group
        validateUserAccessToGroup(recipientGroupMember.getGroup().getId(), userInfo);

        return recipientGroupMember;
    }

    public RecipientGroupMember addNewMember(RecipientGroupMemberDTO recipientGroupMemberDTO, UserInfo userInfo, TokenResponse tokenResponse) {
        RecipientGroup group = recipientGroupService.getOneById(recipientGroupMemberDTO.getGroup_id(), userInfo);

        // Ensure recipient exists
        RecipientResponse recipient = recipientService.getRecipientById(recipientGroupMemberDTO.getRecipient_id(), tokenResponse.getToken());

        RecipientGroupMember recipientGroupMember = toRecipientGroupMember(recipientGroupMemberDTO, group);
        return recipientGroupMemberRepository.save(recipientGroupMember);
    }


    private RecipientGroupMember toRecipientGroupMember(RecipientGroupMemberDTO recipientGroupMemberDTO, RecipientGroup group) {
        RecipientGroupMember recipientGroupMember = new RecipientGroupMember();

        recipientGroupMember.setGroup(group);
        recipientGroupMember.setRecipientId(recipientGroupMemberDTO.getRecipient_id());
        recipientGroupMember.setAddedAt(Instant.now());

        return recipientGroupMember;
    }

    public RecipientGroupMember deleteMember(UUID id, UserInfo userInfo) {
        RecipientGroupMember recipientGroupMember = recipientGroupMemberRepository.findById(id)
                .orElseThrow(() -> new CustomException("Recipient Group Member not found", HttpStatus.NOT_FOUND));

        validateUserAccessToGroup(recipientGroupMember.getGroup().getId(), userInfo);

        recipientGroupMemberRepository.delete(recipientGroupMember);
        return recipientGroupMember;
    }

    private void validateUserAccessToGroup(UUID groupId, UserInfo userInfo) {
        RecipientGroup group = recipientGroupService.getOneById(groupId, userInfo);
        if (group == null) {
            throw new CustomException("Group with ID " + groupId + " not found or access denied", HttpStatus.FORBIDDEN);
        }

        if (!userInfo.getSub().equals(group.getKeycloakUserId().toString())) {
            throw new CustomException("User does not have access to Group with ID " + groupId, HttpStatus.FORBIDDEN);
        }
    }
}
