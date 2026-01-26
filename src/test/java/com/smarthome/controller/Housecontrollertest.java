package com.smarthome.controller;

import com.smarthome.entity.House;
import com.smarthome.service.Houseservice;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
// OLD: import org.springframework.boot.test.mock.mockito.MockBean;
// NEW: Import this instead
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(Housecontroller.class)
 public class Housecontrollertest {

   @Autowired private MockMvc mockMvc;


    @MockitoBean
   private Houseservice houseservice;

    //move device api
     @Test
    public void testMoveDeviceApi() throws Exception {
        doNothing().when(houseservice).moveDevice(1L, 2L);

        mockMvc.perform(put("/api/devices/1/move")
                        .param("targetRoomId", "2"))
                .andExpect(status().isOk())
                 .andExpect(content().string("Device moved successfully"));
    }

    //List Houses API
    @Test
    public void testListHousesApi() throws Exception {
        House h1 = House.builder().id(1L).name("Beach House").build();
         List<House> houses = Arrays.asList(h1);

       when(houseservice.getMyHouses(1L)).thenReturn(houses);

         mockMvc.perform(get("/api/users/1/houses"))
                .andExpect(status().isOk())
                 .andExpect(jsonPath("$[0].name").value("Beach House"));
    }
}