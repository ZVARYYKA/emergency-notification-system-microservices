package dev.zvaryyka.notificationgroupservice.service;


import dev.zvaryyka.notificationgroupservice.dto.RecipientGroupDTO;
import dev.zvaryyka.notificationgroupservice.model.RecipientGroup;
import dev.zvaryyka.notificationgroupservice.model.UserInfo;
import dev.zvaryyka.notificationgroupservice.repository.RecipientGroupRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class RecipientGroupService { //TODO add handlers

    private final RecipientGroupRepository recipientGroupRepository;

    @Autowired
    public RecipientGroupService(RecipientGroupRepository recipientGroupRepository) {
        this.recipientGroupRepository = recipientGroupRepository;
    }

    public List<RecipientGroup> getAllByUser(UserInfo userInfo) {
        return recipientGroupRepository.findAll();
    }
    public RecipientGroup getOneById(UUID id) {
        return recipientGroupRepository.findById(id).orElse(null); //TODO FIX
    }

    public RecipientGroup addNewGroup(RecipientGroupDTO recipientGroup,UserInfo userInfo) {

        return recipientGroupRepository.save(toRecipientGroup(recipientGroup,userInfo));

    }
    public RecipientGroup toRecipientGroup(RecipientGroupDTO recipientGroupDTO,UserInfo userInfo) {

        RecipientGroup recipientGroup = new RecipientGroup();
        recipientGroup.setKeycloakUserId(UUID.fromString(userInfo.getSub()));
        recipientGroup.setName(recipientGroupDTO.getName());
        recipientGroup.setCreatedAt(Instant.now());
        recipientGroup.setUpdatedAt(Instant.now());
        return recipientGroup;
    }

    public RecipientGroup updateGroup(UUID id, RecipientGroupDTO recipientGroupDTO, UserInfo userInfo) {
        //TODO add check users
        //TODO add handles
        RecipientGroup recipientGroup = recipientGroupRepository.getReferenceById(id);

        recipientGroup.setName(recipientGroupDTO.getName());
        recipientGroup.setUpdatedAt(Instant.now());
        return recipientGroup;


    }

    public RecipientGroup deleteGroup(UUID id, UserInfo userInfo) {
        //TODO add check users
        //TODO add handles
        RecipientGroup recipientGroup = recipientGroupRepository.getReferenceById(id);

        recipientGroupRepository.delete(recipientGroup);
        return recipientGroup;
    }
}
