package dev.zvaryyka.notificationservice.service;

import dev.zvaryyka.notificationservice.feignclient.DeviceClient;
import dev.zvaryyka.notificationservice.response.DeviceResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class DeviceService {

    private final DeviceClient deviceClient;


    @Autowired
    public DeviceService(DeviceClient deviceClient) {
        this.deviceClient = deviceClient;
    }

    public List<DeviceResponse> getDevicesByRecipientId(UUID uuid, String token) {


        return deviceClient.getAllDevicesByRecipientId(token,uuid);
    }
}
