package dev.zvaryyka.recipientservice.controller;


import dev.zvaryyka.recipientservice.models.Device;
import dev.zvaryyka.recipientservice.request.DeviceDTO;
import dev.zvaryyka.recipientservice.response.UserInfo;
import dev.zvaryyka.recipientservice.service.DeviceService;
import dev.zvaryyka.recipientservice.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/devices")
public class DeviceController {
    private final DeviceService deviceService;
    private final UserService userService;


    @Autowired
    public DeviceController(DeviceService deviceService, UserService userService) {
        this.deviceService = deviceService;
        this.userService = userService;
    }

    @GetMapping("/getAllByRecipientId/{id}")
    public ResponseEntity<List<Device>> getAllDevicesByRecipientId(@PathVariable UUID id) {
        UserInfo userInfo = userService.getUserInfoByToken();
        return ResponseEntity.ok(deviceService.getAllDevicesByRecipientId(id,userInfo));
    }

    @GetMapping("/getByDeviceId/{id}")
    public ResponseEntity<Device> getDeviceByDeviceId(@PathVariable UUID id) {
        UserInfo userInfo = userService.getUserInfoByToken();
        return ResponseEntity.ok(deviceService.getDeviceByDeviceId(id,userInfo));
    }

    @PostMapping("/add")
    public ResponseEntity<Device> addNewDevice(@RequestBody DeviceDTO deviceDTO) {
        UserInfo userInfo = userService.getUserInfoByToken();

        return ResponseEntity.ok(deviceService.addNewDevice(deviceDTO,userInfo));
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<Device> updateDevice(@PathVariable UUID id, @RequestBody DeviceDTO deviceDTO) {
        UserInfo userInfo = userService.getUserInfoByToken();

        return ResponseEntity.ok(deviceService.updateDevice(id, deviceDTO,userInfo));
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<Device> deleteDevice(@PathVariable UUID id) {
        UserInfo userInfo = userService.getUserInfoByToken();

        return ResponseEntity.ok(deviceService.deleteDevice(id,userInfo));
    }

}
