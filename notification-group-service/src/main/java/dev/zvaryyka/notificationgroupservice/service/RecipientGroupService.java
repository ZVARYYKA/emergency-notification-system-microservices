package dev.zvaryyka.notificationgroupservice.service;

import dev.zvaryyka.notificationgroupservice.dto.RecipientGroupDTO;
import dev.zvaryyka.notificationgroupservice.exception.CustomException;
import dev.zvaryyka.notificationgroupservice.model.RecipientGroup;
import dev.zvaryyka.notificationgroupservice.model.UserInfo;
import dev.zvaryyka.notificationgroupservice.repository.RecipientGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class RecipientGroupService {

    private final RecipientGroupRepository recipientGroupRepository;

    @Autowired
    public RecipientGroupService(RecipientGroupRepository recipientGroupRepository) {
        this.recipientGroupRepository = recipientGroupRepository;
    }

    public List<RecipientGroup> getAllByUser(UserInfo userInfo) {
        return recipientGroupRepository.findByKeycloakUserId(UUID.fromString(userInfo.getSub()));
    }

    public RecipientGroup getOneById(UUID id, UserInfo userInfo) {
        RecipientGroup recipientGroup = recipientGroupRepository.findById(id)
                .orElseThrow(() -> new CustomException("Recipient Group not found", HttpStatus.NOT_FOUND));

        if (!recipientGroup.getKeycloakUserId().equals(UUID.fromString(userInfo.getSub()))) {
            throw new CustomException("You don't have access to this Recipient Group", HttpStatus.FORBIDDEN);
        }

        return recipientGroup;
    }

    public RecipientGroup addNewGroup(RecipientGroupDTO recipientGroupDTO, UserInfo userInfo) {
        RecipientGroup recipientGroup = toRecipientGroup(recipientGroupDTO, userInfo);
        return recipientGroupRepository.save(recipientGroup);
    }

    public RecipientGroup toRecipientGroup(RecipientGroupDTO recipientGroupDTO, UserInfo userInfo) {
        RecipientGroup recipientGroup = new RecipientGroup();
        recipientGroup.setKeycloakUserId(UUID.fromString(userInfo.getSub()));
        recipientGroup.setName(recipientGroupDTO.getName());
        recipientGroup.setCreatedAt(Instant.now());
        recipientGroup.setUpdatedAt(Instant.now());
        return recipientGroup;
    }

    public RecipientGroup updateGroup(UUID id, RecipientGroupDTO recipientGroupDTO, UserInfo userInfo) {
        RecipientGroup recipientGroup = recipientGroupRepository.findById(id)
                .orElseThrow(() -> new CustomException("Recipient Group not found", HttpStatus.NOT_FOUND));

        if (!recipientGroup.getKeycloakUserId().equals(UUID.fromString(userInfo.getSub()))) {
            throw new CustomException("You don't have access to this Recipient Group", HttpStatus.FORBIDDEN);
        }

        recipientGroup.setName(recipientGroupDTO.getName());
        recipientGroup.setUpdatedAt(Instant.now());
        return recipientGroupRepository.save(recipientGroup);
    }

    public RecipientGroup deleteGroup(UUID id, UserInfo userInfo) {
        RecipientGroup recipientGroup = recipientGroupRepository.findById(id)
                .orElseThrow(() -> new CustomException("Recipient Group not found", HttpStatus.NOT_FOUND));

        if (!recipientGroup.getKeycloakUserId().equals(UUID.fromString(userInfo.getSub()))) {
            throw new CustomException("You don't have access to this Recipient Group", HttpStatus.FORBIDDEN);
        }

        recipientGroupRepository.delete(recipientGroup);
        return recipientGroup;
    }
}
