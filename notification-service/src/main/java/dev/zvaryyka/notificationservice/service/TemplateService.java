package dev.zvaryyka.notificationservice.service;

import dev.zvaryyka.notificationservice.exception.CustomException;
import dev.zvaryyka.notificationservice.feignclient.TemplateClient;
import dev.zvaryyka.notificationservice.response.MessageTemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class TemplateService {

    private final TemplateClient templateClient;

    @Autowired
    public TemplateService(TemplateClient templateClient) {
        this.templateClient = templateClient;
    }

    public MessageTemplateResponse getMessageTemplateByTemplateId(UUID templateId, String token) {
        try {
            MessageTemplateResponse response = templateClient.getMessageTemplateByTemplateId(token, templateId);

            if (response == null) {
                throw new CustomException("Template not found with ID " + templateId, HttpStatus.NOT_FOUND);
            }

            return response;
        } catch (Exception ex) {
            // Здесь вы можете обработать конкретные исключения, такие как FeignException, HttpClientErrorException, и т.д.
            throw new CustomException("Failed to fetch template with ID " + templateId + ": " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
