package dev.zvaryyka.recipientservice.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDTO {

    private UUID recipientId;
    private String deviceName;
    private String deviceType;
    private String deviceToken;

}
