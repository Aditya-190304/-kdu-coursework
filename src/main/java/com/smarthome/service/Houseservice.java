package com.smarthome.service;

import com.smarthome.dto.Registerrequest;
import com.smarthome.entity.*;
import com.smarthome.exception.Resourceexistsexception;
import com.smarthome.exception.Resourcenotfoundexception;
import com.smarthome.repo.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

@Service
@Slf4j
public class Houseservice {

     @Autowired private Houserepo houseRepository;
    @Autowired private Userrepo userRepository;
   @Autowired private Roomrepo roomRepository;
    @Autowired private Devicerepo deviceRepository;
    @Autowired private Inventoryrepo inventoryRepository;


    @Transactional
    public House createHouse(Long userId, String houseName, String address) {
         log.info("Creating house '{}' for user {}", houseName, userId);

       User user = userRepository.findById(userId)
                .orElseThrow(() -> new Resourcenotfoundexception("User not found with ID: " + userId));

          House house = House.builder()
                .name(houseName)
                 .address(address)
                .admin(user)
                .build();

        house.getMembers().add(user);
        return houseRepository.save(house);
    }

    // list all the houses this user is part of
     public List<House> getMyHouses(Long userId) {
       User user = userRepository.findById(userId)
                .orElseThrow(() -> new Resourcenotfoundexception("User not found with ID: " + userId));
        return user.getHouses();
    }

    // lets the admin invite a new member to the house
    @Transactional
    public void addMember(Long adminId, Long houseId, Long newMemberId) {
       House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new Resourcenotfoundexception("House not found with ID: " + houseId));

         if (!house.getAdmin().getId().equals(adminId)) {
            throw new RuntimeException("Only Admin can add members");
       }

        User newMember = userRepository.findById(newMemberId)
                .orElseThrow(() -> new Resourcenotfoundexception("User to add not found with ID: " + newMemberId));

        if (house.getMembers().contains(newMember)) {
            throw new Resourceexistsexception("User is already a member of this house");
        }

        house.getMembers().add(newMember);
         houseRepository.save(house);
    }

    // fix the physical address if it changes
    @Transactional
      public House updateAddress(Long adminId, Long houseId, String newAddress) {
       House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new Resourcenotfoundexception("House not found with ID: " + houseId));

         if (!house.getAdmin().getId().equals(adminId)) {
            throw new RuntimeException("Only Admin can update address");
       }

        house.setAddress(newAddress);
         return houseRepository.save(house);
    }

    // give admin rights to someone else in the house
    @Transactional
    public void transferOwnership(Long currentAdminId, Long houseId, Long newAdminId) {
       House house = houseRepository.findById(houseId)
                 .orElseThrow(() -> new Resourcenotfoundexception("House not found with ID: " + houseId));

         if (!house.getAdmin().getId().equals(currentAdminId)) {
           throw new RuntimeException("Only the current Admin can transfer ownership");
        }

        User newAdmin = userRepository.findById(newAdminId)
                .orElseThrow(() -> new Resourcenotfoundexception("New admin user not found"));

       if (!house.getMembers().contains(newAdmin)) {
             throw new RuntimeException("New Admin must be a member of the house first");
        }

         house.setAdmin(newAdmin);
        houseRepository.save(house);
    }


    @Transactional
    public Room createRoom(Long userId, Long houseId, String roomName) {
        log.info("Creating room '{}' for house {}", roomName, houseId);

        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new Resourcenotfoundexception("House not found with ID: " + houseId));

        // security check: regular members shouldn't be building rooms
        if (!house.getAdmin().getId().equals(userId)) {
            throw new RuntimeException("Only Admin can create rooms");
        }


       if (roomRepository.existsByNameAndHouseId(roomName, houseId)) {
              throw new Resourceexistsexception("Room '" + roomName + "' already exists in this house!");
        }

        Room room = Room.builder()
                .name(roomName)
                 .house(house)
                .build();

        return roomRepository.save(room);
   }

      @Transactional
    public Device addDeviceToRoom(Long userId, Registerrequest request) {
       log.info("Adding device {} to room {}", request.getKickstonId(), request.getRoomId());

        // check if the device credentials actually exist in our inventory
         Inventory inventory = inventoryRepository
               .checkCredentials(
                         request.getKickstonId(),
                        request.getDeviceUsername(),
                        request.getDevicePassword())
               .orElseThrow(() -> new Resourcenotfoundexception("Invalid Device Credentials or Device not found"));

        // make sure this specific device isn't already used somewhere else
          if (deviceRepository.existsByInventoryDetails(inventory)) {
            throw new Resourceexistsexception("Device is already registered to a room!");
        }

        // find the room we are putting it in
        Room room = roomRepository.findById(request.getRoomId())
              .orElseThrow(() -> new Resourcenotfoundexception("Room not found with ID: " + request.getRoomId()));

        // only admins are allowed to register new hardware
      if (!room.getHouse().getAdmin().getId().equals(userId)) {
            throw new RuntimeException("Only Admin can add devices");
        }

          Device device = Device.builder()
                .inventoryDetails(inventory)
                  .room(room)
                .customName("New Device")
                .build();

        return deviceRepository.save(device);
    }

    @Transactional
    public void moveDevice(Long deviceId, Long targetRoomId) {
        Device device = deviceRepository.findById(deviceId)
                  .orElseThrow(() -> new Resourcenotfoundexception("Device not found with ID: " + deviceId));

       Room targetRoom = roomRepository.findById(targetRoomId)
                .orElseThrow(() -> new Resourcenotfoundexception("Target Room not found with ID: " + targetRoomId));

        if (!device.getRoom().getHouse().getId().equals(targetRoom.getHouse().getId())) {
            throw new RuntimeException("Cannot move device to different house");
        }

        device.setRoom(targetRoom);
        deviceRepository.save(device);
    }

public List<Room> getRoomsWithDevices(Long houseId) {
        House house = houseRepository.findById(houseId)
                .orElseThrow(() -> new Resourcenotfoundexception("House not found with ID: " + houseId));
        return house.getRooms();
    }
}