package dev.zvaryyka.recipientservice.controller;


import dev.zvaryyka.recipientservice.models.Device;
import dev.zvaryyka.recipientservice.request.DeviceDTO;
import dev.zvaryyka.recipientservice.service.DeviceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    private final DeviceService deviceService;

    @Autowired
    public DeviceController(DeviceService deviceService) {
        this.deviceService = deviceService;
    }

    @GetMapping("/getAllByRecipientId/{id}")
    public ResponseEntity<List<Device>> getAllDevicesByRecipientId(@PathVariable UUID id) {
        return ResponseEntity.ok(deviceService.getAllDevicesByRecipientId(id));
    }
    @GetMapping("/getByDeviceId/{id}")
    public ResponseEntity<Device> getDeviceByDeviceId(@PathVariable UUID id) {
        return ResponseEntity.ok(deviceService.getDeviceByDeviceId(id));
    }
    @PostMapping("/add")
    public ResponseEntity<Device> addNewDevice(@RequestBody DeviceDTO deviceDTO) {
        return ResponseEntity.ok(deviceService.addNewDevice(deviceDTO));
    }
    @PutMapping("/update/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable UUID id, @RequestBody DeviceDTO deviceDTO) {
        return ResponseEntity.ok(deviceService.updateDevice(id, deviceDTO));
    }
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Device> deleteDevice(@PathVariable UUID id) {
        return ResponseEntity.ok(deviceService.deleteDevice(id));
    }

}
