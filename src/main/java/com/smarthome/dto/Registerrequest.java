package com.smarthome.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class Registerrequest {

    // identifier for the room where device will be installed
    @NotNull(message = "room id is required")
     private Long roomId;

    // unique serial number for the device hardware
    @NotBlank(message = "kickston id cannot be empty")
    private String kickstonId;

    // username for device authentication
     @NotBlank(message = "device username cannot be empty")
     private String deviceUsername;

    // password for device authentication
    @NotBlank(message = "device password cannot be empty")
   private String devicePassword;
}