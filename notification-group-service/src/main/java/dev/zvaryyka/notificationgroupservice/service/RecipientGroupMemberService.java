package dev.zvaryyka.notificationgroupservice.service;


import dev.zvaryyka.notificationgroupservice.dto.RecipientGroupMemberDTO;
import dev.zvaryyka.notificationgroupservice.feignclient.RecipientClient;
import dev.zvaryyka.notificationgroupservice.model.RecipientGroup;
import dev.zvaryyka.notificationgroupservice.model.RecipientGroupMember;
import dev.zvaryyka.notificationgroupservice.model.UserInfo;
import dev.zvaryyka.notificationgroupservice.repository.RecipientGroupMemberRepository;
import dev.zvaryyka.notificationgroupservice.response.RecipientResponse;
import dev.zvaryyka.notificationgroupservice.response.TokenResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.IllegalFormatException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecipientGroupMemberService {

    private final RecipientGroupMemberRepository recipientGroupMemberRepository;
    private final RecipientGroupService recipientGroupService;
    private final RecipientClient recipientClient;

    @Autowired
    public RecipientGroupMemberService(
            RecipientGroupMemberRepository recipientGroupMemberRepository, RecipientGroupService recipientGroupService, RecipientClient recipientClient) {
        this.recipientGroupMemberRepository = recipientGroupMemberRepository;
        this.recipientGroupService = recipientGroupService;
        this.recipientClient = recipientClient;
    }

    public List<RecipientGroupMember> getAllByGroupId(UUID groupId, UserInfo userInfo) {
        //TODO check user
        return recipientGroupMemberRepository.findByGroupId(groupId);
    }

    public RecipientGroupMember getOneById(UUID uuid) {
        return recipientGroupMemberRepository.getReferenceById(uuid);
    }

    public RecipientGroupMember addNewMember(RecipientGroupMemberDTO recipientGroupMemberDTO,
                                             UserInfo userInfo,

                                             TokenResponse tokenResponse) {
        //TODO Check user
        return recipientGroupMemberRepository.save(toRecipientGroupMember(recipientGroupMemberDTO, tokenResponse));


    }

    private RecipientGroupMember toRecipientGroupMember(
            RecipientGroupMemberDTO recipientGroupMemberDTO,
            TokenResponse tokenResponse) {

        //TODO Add Handles
        RecipientGroupMember recipientGroupMember = new RecipientGroupMember();

        recipientGroupMember.setGroup(
                recipientGroupService.getOneById(recipientGroupMemberDTO.getGroup_id()));

        Optional<RecipientResponse> recipient = recipientClient.getOneById(
                "Bearer " + tokenResponse.getToken(),
                recipientGroupMemberDTO.getRecipient_id()
        );
        if (recipient.isEmpty()) {
            //TODO add exception
            throw new IllegalArgumentException("Recipient not found with ID " + recipientGroupMemberDTO.getRecipient_id());
        }
        else {
            recipientGroupMember.setRecipientId(recipient.get().getId());
            RecipientGroup group = recipientGroupService.getOneById(recipientGroupMemberDTO.getGroup_id());
            if (group == null) {
                throw new IllegalArgumentException("Group with ID " + recipientGroupMemberDTO.getGroup_id() + " not found.");
            }
            recipientGroupMember.setGroup(group);        }
        recipientGroupMember.setAddedAt(Instant.now());

        return recipientGroupMember;

    }

    public RecipientGroupMember deleteMember(UUID id) {
        //TODO user check
        //TODO add handles

        RecipientGroupMember recipientGroupMember = recipientGroupMemberRepository.getReferenceById(id);
        recipientGroupMemberRepository.delete(recipientGroupMember);
        return recipientGroupMember;

    }
}
