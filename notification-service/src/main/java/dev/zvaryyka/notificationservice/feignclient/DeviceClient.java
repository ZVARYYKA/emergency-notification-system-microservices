package dev.zvaryyka.notificationservice.feignclient;


import dev.zvaryyka.notificationservice.response.DeviceResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;

import java.util.List;
import java.util.UUID;

@FeignClient(name = "device-service", url = "http://localhost:8765/devices/")
public interface DeviceClient {

    @GetMapping("/getAllByRecipientId/{id}")
    List<DeviceResponse> getAllDevicesByRecipientId(@RequestHeader("Authorization") String token,
                                                    @PathVariable("id") UUID recipientId);
}