package dev.zvaryyka.notificationservice.feignclient;

import dev.zvaryyka.notificationservice.response.RecipientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;
import java.util.UUID;

@FeignClient(name = "recipientClient", url = "http://localhost:8765")
public interface RecipientClient {

    @GetMapping("/recipients/getById/{recipientId}")
    Optional<RecipientResponse> getOneById(@RequestHeader("Authorization") String token, @PathVariable UUID recipientId);

}
