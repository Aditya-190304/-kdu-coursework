package com.smarthome.controller;

import com.smarthome.dto.Registerrequest;
import com.smarthome.entity.*;
import com.smarthome.service.Houseservice;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api")
@Slf4j
@Validated
public class Housecontroller {

    @Autowired
    private Houseservice houseService;
    //creates a new house and assigns the creator as the admin.

    @PostMapping("/houses")

public ResponseEntity<House> createHouse(
             @RequestParam @Min(value = 1, message = "User ID must be valid") Long userId,
              @RequestParam String name,
             @RequestParam String address) {
         log.info("Request received to create house: {}", name);
        return ResponseEntity.ok(houseService.createHouse(userId, name, address));
    }
    //Retrieves all houses associated with a specific user.
    @GetMapping("/users/{userId}/houses")
     public ResponseEntity<List<House>> getMyHouses(
     @PathVariable @Min(value = 1, message = "User ID must be valid") Long userId) {
        return ResponseEntity.ok(houseService.getMyHouses(userId));
    }


    // adds a new member to an existing house.
    // requires Admin privileges.

   @PostMapping("/houses/{houseId}/members")
    public ResponseEntity<String> addMember(
             @RequestParam @Min(value = 1, message = "Admin ID must be valid") Long adminId,
            @PathVariable @Min(value = 1, message = "House ID must be valid") Long houseId,
            @RequestParam @Min(value = 1, message = "New Member ID must be valid") Long newMemberId) {
         houseService.addMember(adminId, houseId, newMemberId);
        return ResponseEntity.ok("Member added successfully.");
    }


    //updates the address of a specific house.

     @PutMapping("/houses/{houseId}/address")
    public ResponseEntity<House> updateAddress(
            @RequestParam @Min(value = 1, message = "Admin ID must be valid") Long adminId,
            @PathVariable @Min(value = 1, message = "House ID must be valid") Long houseId,
             @RequestParam String address) {
      return ResponseEntity.ok(houseService.updateAddress(adminId, houseId, address));
    }


//transfers house ownership to another member.

     @PutMapping("/houses/{houseId}/admin")
    public ResponseEntity<String> transferOwnership(
            @RequestParam @Min(value = 1, message = "Admin ID must be valid") Long adminId,
            @PathVariable @Min(value = 1, message = "House ID must be valid") Long houseId,
            @RequestParam @Min(value = 1, message = "New Admin ID must be valid") Long newAdminId) {
         houseService.transferOwnership(adminId, houseId, newAdminId);
        return ResponseEntity.ok("Ownership transferred successfully.");
    }

    // room Management Endpoints

    @PostMapping("/rooms")
     public ResponseEntity<Room> createRoom(
            @RequestParam @Min(value = 1) Long userId,
             @RequestParam @Min(value = 1) Long houseId,
            @RequestParam String name) {
        log.info("Request received to create room: {}", name);
         return ResponseEntity.ok(houseService.createRoom(userId, houseId, name));
    }

     @GetMapping("/houses/{houseId}/rooms")
    public ResponseEntity<List<Room>> getRoomsAndDevices(
             @PathVariable @Min(value = 1, message = "House ID must be valid") Long houseId) {
         return ResponseEntity.ok(houseService.getRoomsWithDevices(houseId));
    }

    // device Management Endpoints

    @PostMapping("/devices")
     public ResponseEntity<Device> addDevice(
            @RequestParam @Min(value = 1, message = "User ID must be valid") Long userId,
             @Valid @RequestBody Registerrequest request) {
        log.info("Request received to register device");
       return ResponseEntity.ok(houseService.addDeviceToRoom(userId, request));
    }

    @PutMapping("/devices/{deviceId}/move")
    public ResponseEntity<String> moveDevice(
            @PathVariable @Min(value = 1, message = "Device ID must be valid") Long deviceId,
            @RequestParam @Min(value = 1, message = "Target Room ID must be valid") Long targetRoomId) {
        houseService.moveDevice(deviceId, targetRoomId);
        return ResponseEntity.ok("Device moved successfully.");
    }
}