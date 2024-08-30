package dev.zvaryyka.notificationgroupservice.service;

import dev.zvaryyka.notificationgroupservice.exception.CustomException;
import dev.zvaryyka.notificationgroupservice.feignclient.RecipientClient;
import dev.zvaryyka.notificationgroupservice.response.RecipientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.UUID;

@Service
public class RecipientService {

    private final RecipientClient recipientClient;

    @Autowired
    public RecipientService(RecipientClient recipientClient) {
        this.recipientClient = recipientClient;
    }

    public RecipientResponse getRecipientById(UUID recipientId, String token) {
        Optional<RecipientResponse> recipientResponse = recipientClient.getOneById("Bearer " + token, recipientId);
        if (recipientResponse.isEmpty()) {
            throw new CustomException("Recipient not found with ID " + recipientId, HttpStatus.NOT_FOUND);
        }
        return recipientResponse.get();
    }
}
