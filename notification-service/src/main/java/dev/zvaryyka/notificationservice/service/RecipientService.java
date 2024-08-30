package dev.zvaryyka.notificationservice.service;

import dev.zvaryyka.notificationservice.exception.CustomException;
import dev.zvaryyka.notificationservice.feignclient.RecipientClient;
import dev.zvaryyka.notificationservice.response.RecipientResponse;
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

    public RecipientResponse getRecipientByRecipientId(UUID uuid, String token) {
        Optional<RecipientResponse> recipient = recipientClient.getOneById(token, uuid);

        // Если объект не найден, выбросить CustomException
        if (recipient.isEmpty()) {
            throw new CustomException("Recipient not found with ID " + uuid, HttpStatus.NOT_FOUND);
        }

        return recipient.get();
    }
}
