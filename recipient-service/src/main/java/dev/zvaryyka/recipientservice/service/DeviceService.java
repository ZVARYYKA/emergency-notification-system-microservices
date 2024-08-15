package dev.zvaryyka.recipientservice.service;

import dev.zvaryyka.recipientservice.models.Device;
import dev.zvaryyka.recipientservice.models.Recipient;
import dev.zvaryyka.recipientservice.repository.DeviceRepository;
import dev.zvaryyka.recipientservice.repository.RecipientRepository;
import dev.zvaryyka.recipientservice.request.DeviceDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

@Service
public class DeviceService {
    private final DeviceRepository deviceRepository;
    private final RecipientRepository recipientRepository;

    @Autowired
    public DeviceService(DeviceRepository deviceRepository, RecipientRepository recipientRepository) {
        this.deviceRepository = deviceRepository;
        this.recipientRepository = recipientRepository;
    }
    private Device toDevice(DeviceDTO deviceDTO) {

        //TODO Add handling recipient not found
        Recipient recipient = recipientRepository.findById(deviceDTO.getRecipientId()).get();

        Device device = new Device();
        device.setRecipient(recipient);
        device.setDeviceName(deviceDTO.getDeviceName());
        device.setDeviceType(deviceDTO.getDeviceType());
        device.setDeviceToken(deviceDTO.getDeviceToken());
        return device;

    }
    public Device addNewDevice(DeviceDTO deviceDTO) {
        Device device = toDevice(deviceDTO);
        device.setCreatedAt(Instant.now());
        device.setUpdatedAt(Instant.now());
        return deviceRepository.save(device);
    }
    public Device deleteDevice(UUID id) {
        //TODO handle not found
        Device device = deviceRepository.findById(id).get();
        deviceRepository.delete(device);
        return device;
    }
    public Device updateDevice(UUID id, DeviceDTO deviceDTO) {
        //TODO handle not found
        Device device = deviceRepository.findById(id).get();
        device.setDeviceName(deviceDTO.getDeviceName());
        device.setDeviceType(deviceDTO.getDeviceType());
        device.setDeviceToken(deviceDTO.getDeviceToken());
        device.setUpdatedAt(Instant.now());
        return deviceRepository.save(device);
    }

    public List<Device> getAllDevicesByRecipientId(UUID id) {
        //TODO handle not found
        Recipient recipient = recipientRepository.findById(id).get();
        return deviceRepository.findAllByRecipient(recipient);
    }

    public Device getDeviceByDeviceId(UUID id) {
        //TODO handle not found
        return deviceRepository.findById(id).get();
    }
}
