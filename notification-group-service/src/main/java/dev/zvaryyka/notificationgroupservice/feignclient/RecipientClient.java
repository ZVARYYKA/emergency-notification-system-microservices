package dev.zvaryyka.notificationgroupservice.feignclient;

import dev.zvaryyka.notificationgroupservice.response.RecipientResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.Optional;
import java.util.UUID;

@FeignClient(name = "recipientClient", url = "http://localhost:8080")
public interface RecipientClient {

    @GetMapping("/recipients/getById/{recipientId}")
    Optional<RecipientResponse> getOneById(@RequestHeader("Authorization") String token, @PathVariable UUID recipientId);

}
