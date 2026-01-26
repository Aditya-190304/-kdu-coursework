package com.smarthome.service;

import com.smarthome.entity.*;
import com.smarthome.repo.*;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class Houseservicetest {

    @Mock private Devicerepo deviceRepository;
     @Mock private Roomrepo roomRepository;
    @Mock private Userrepo userRepository;

    @InjectMocks private Houseservice houseService;

    //Move Device
    @Test
   public void testMoveDevice_Success() {

        House house = House.builder().id(1L).build();
         Room sourceRoom = Room.builder().id(1L).house(house).build();
        Room targetRoom = Room.builder().id(2L).house(house).build(); // Same House

          Device device = Device.builder().id(100L).room(sourceRoom).build();


      when(deviceRepository.findById(100L)).thenReturn(Optional.of(device));
        when(roomRepository.findById(2L)).thenReturn(Optional.of(targetRoom));


         houseService.moveDevice(100L, 2L);


        assertEquals(targetRoom, device.getRoom());
        verify(deviceRepository).save(device);
    }

    //List Houses
    @Test
    public void testGetMyHouses_Success() {
        House house1 = House.builder().name("Villa").build();
         House house2 = House.builder().name("Apartment").build();
       User user = User.builder().id(1L).houses(Arrays.asList(house1, house2)).build();


        when(userRepository.findById(1L)).thenReturn(Optional.of(user));


         List<House> result = houseService.getMyHouses(1L);


        assertEquals(2, result.size());
         assertEquals("Villa", result.get(0).getName());
    }
}