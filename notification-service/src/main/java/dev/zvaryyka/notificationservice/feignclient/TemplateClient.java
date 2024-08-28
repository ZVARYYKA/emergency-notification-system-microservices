package dev.zvaryyka.notificationservice.feignclient;

import dev.zvaryyka.notificationservice.response.MessageTemplateResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.UUID;

@FeignClient(name = "template-service", url = "http://localhost:8765/templates")
public interface TemplateClient {

    @GetMapping("/getById/{id}")
    MessageTemplateResponse getMessageTemplateByTemplateId(@RequestHeader("Authorization") String token,
                                                           @PathVariable("id") UUID templateI);
}