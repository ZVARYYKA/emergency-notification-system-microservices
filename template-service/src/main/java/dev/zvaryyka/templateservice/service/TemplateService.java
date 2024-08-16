package dev.zvaryyka.templateservice.service;

import dev.zvaryyka.templateservice.dto.MessageTemplateDTO;
import dev.zvaryyka.templateservice.models.MessageTemplate;
import dev.zvaryyka.templateservice.models.UserInfo;
import dev.zvaryyka.templateservice.repository.TemplateRepository;
import jakarta.ws.rs.NotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.time.OffsetDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class TemplateService {

    private final TemplateRepository templateRepository;

    @Autowired
    public TemplateService(TemplateRepository templateRepository) {
        this.templateRepository = templateRepository;
    }

    public List<MessageTemplate> getAllMessageTemplatesByUserId(UserInfo userInfo) {
        return templateRepository.findAllByKeycloakUserId(UUID.fromString(userInfo.getSub()));
    }

    public MessageTemplate getMessageTemplateByTemplateId(UUID id, UserInfo userInfo) {
        MessageTemplate messageTemplate = templateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("MessageTemplate not found"));

        if (!messageTemplate.getKeycloakUserId().equals(UUID.fromString(userInfo.getSub()))) {
            throw new AccessDeniedException("You do not have access to this template");
        }

        return messageTemplate;
    }

    public MessageTemplate addNewMessageTemplate(MessageTemplateDTO messageTemplateDTO, UserInfo userInfo) {
        MessageTemplate messageTemplate = toMessageTemplate(messageTemplateDTO, userInfo);
        messageTemplate.setCreatedAt(OffsetDateTime.now());
        messageTemplate.setUpdatedAt(OffsetDateTime.now());
        return templateRepository.save(messageTemplate);
    }

    public MessageTemplate updateMessageTemplate(UUID id, MessageTemplateDTO messageTemplateDTO, UserInfo userInfo) {
        MessageTemplate messageTemplate = templateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("MessageTemplate not found"));

        if (!messageTemplate.getKeycloakUserId().equals(UUID.fromString(userInfo.getSub()))) {
            throw new AccessDeniedException("You do not have access to update this template");
        }

        messageTemplate.setSubject(messageTemplateDTO.getSubject());
        messageTemplate.setBody(messageTemplateDTO.getBody());
        messageTemplate.setUpdatedAt(OffsetDateTime.now());
        return templateRepository.save(messageTemplate);
    }

    public MessageTemplate deleteMessageTemplate(UUID id, UserInfo userInfo) {
        MessageTemplate messageTemplate = templateRepository.findById(id)
                .orElseThrow(() -> new NotFoundException("MessageTemplate not found"));

        if (!messageTemplate.getKeycloakUserId().equals(UUID.fromString(userInfo.getSub()))) {
            throw new AccessDeniedException("You do not have access to delete this template");
        }

        templateRepository.delete(messageTemplate);
        return messageTemplate;
    }

    private MessageTemplate toMessageTemplate(MessageTemplateDTO messageTemplateDTO, UserInfo userInfo) {
        MessageTemplate messageTemplate = new MessageTemplate();
        messageTemplate.setSubject(messageTemplateDTO.getSubject());
        messageTemplate.setBody(messageTemplateDTO.getBody());
        messageTemplate.setKeycloakUserId(UUID.fromString(userInfo.getSub()));
        return messageTemplate;
    }
}
