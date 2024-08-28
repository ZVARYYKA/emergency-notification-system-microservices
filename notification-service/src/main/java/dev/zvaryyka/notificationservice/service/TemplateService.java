package dev.zvaryyka.notificationservice.service;

import com.netflix.discovery.converters.Auto;
import dev.zvaryyka.notificationservice.feignclient.TemplateClient;
import dev.zvaryyka.notificationservice.response.MessageTemplateResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class TemplateService {

    private final TemplateClient templateClient;


    @Autowired
    public TemplateService(TemplateClient templateClient) {
        this.templateClient = templateClient;
    }


    public MessageTemplateResponse getMessageTemplateByTemplateId(UUID templateId, String token) {

        return templateClient.getMessageTemplateByTemplateId(token, templateId);
    }
}
