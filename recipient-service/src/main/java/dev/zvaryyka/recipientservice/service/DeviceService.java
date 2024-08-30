package dev.zvaryyka.recipientservice.service;

import dev.zvaryyka.recipientservice.exception.CustomException;
import dev.zvaryyka.recipientservice.models.Device;
import dev.zvaryyka.recipientservice.models.Recipient;
import dev.zvaryyka.recipientservice.repository.DeviceRepository;
import dev.zvaryyka.recipientservice.repository.RecipientRepository;
import dev.zvaryyka.recipientservice.request.DeviceDTO;
import dev.zvaryyka.recipientservice.response.UserInfo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.util.List;
import java.util.Objects;
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

        Recipient recipient = recipientRepository.findById(deviceDTO.getRecipientId())
                .orElseThrow(() -> new CustomException("Recipient not found", HttpStatus.NOT_FOUND));

        Device device = new Device();
        device.setRecipient(recipient);
        device.setDeviceName(deviceDTO.getDeviceName());
        device.setDeviceType(deviceDTO.getDeviceType());
        device.setDeviceToken(deviceDTO.getDeviceToken());
        return device;

    }
    public Device addNewDevice(DeviceDTO deviceDTO, UserInfo userInfo) {
        Recipient recipient = recipientRepository.findById(deviceDTO.getRecipientId())
                .orElseThrow(() -> new CustomException("Recipient not found", HttpStatus.NOT_FOUND));

        if (!Objects.equals(recipient.getKeycloakUserId(), userInfo.getSub())) {
            throw new CustomException("You don't have access to this recipient", HttpStatus.FORBIDDEN);
        }
        Device device = toDevice(deviceDTO);
        device.setCreatedAt(Instant.now());
        device.setUpdatedAt(Instant.now());
        return deviceRepository.save(device);
    }
    public Device deleteDevice(UUID id, UserInfo userInfo) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new CustomException("Device not found", HttpStatus.NOT_FOUND));

        if (!Objects.equals(device.getRecipient().getKeycloakUserId(), userInfo.getSub())) {
            throw new CustomException("You don't have access to this device", HttpStatus.FORBIDDEN);
        }

        deviceRepository.delete(device);
        return device;
    }

    public Device updateDevice(UUID id, DeviceDTO deviceDTO, UserInfo userInfo) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new CustomException("Device not found", HttpStatus.NOT_FOUND));

        if (!Objects.equals(device.getRecipient().getKeycloakUserId(), userInfo.getSub())) {
            throw new CustomException("You don't have access to this device", HttpStatus.FORBIDDEN);
        }

        device.setDeviceName(deviceDTO.getDeviceName());
        device.setDeviceType(deviceDTO.getDeviceType());
        device.setDeviceToken(deviceDTO.getDeviceToken());
        device.setUpdatedAt(Instant.now());

        return deviceRepository.save(device);
    }


    public List<Device> getAllDevicesByRecipientId(UUID id, UserInfo userInfo) {
        Recipient recipient = recipientRepository.findById(id)
                .orElseThrow(() -> new CustomException("Recipient not found", HttpStatus.NOT_FOUND));

        if (!Objects.equals(recipient.getKeycloakUserId(), userInfo.getSub())) {
            throw new CustomException("You don't have access to this recipient", HttpStatus.FORBIDDEN);
        }

        return deviceRepository.findAllByRecipient(recipient);
    }

    public Device getDeviceByDeviceId(UUID id, UserInfo userInfo) {
        Device device = deviceRepository.findById(id)
                .orElseThrow(() -> new CustomException("Device not found", HttpStatus.NOT_FOUND));

        if (!Objects.equals(device.getRecipient().getKeycloakUserId(), userInfo.getSub())) {
            throw new CustomException("You don't have access to this device", HttpStatus.FORBIDDEN);
        }

        return device;
    }
}
