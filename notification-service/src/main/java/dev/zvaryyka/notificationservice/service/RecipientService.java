package dev.zvaryyka.notificationservice.service;

import dev.zvaryyka.notificationservice.feignclient.RecipientClient;
import dev.zvaryyka.notificationservice.response.RecipientResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RecipientService {

    private final RecipientClient recipientClient;

    @Autowired
    public RecipientService(RecipientClient recipientClient) {
        this.recipientClient = recipientClient;
    }

    public RecipientResponse getRecipientByRecipientId(UUID uuid, String token) {

        //TODO hander
        return recipientClient.getOneById(token, uuid).get();
    }
}
