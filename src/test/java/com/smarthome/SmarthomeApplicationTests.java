package com.smarthome;
import com.smarthome.entity.*;
import com.smarthome.repo.*;
import jakarta.persistence.EntityManager;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@Transactional
// stop the main data.sql from running so we don't get id conflicts
@TestPropertySource(properties = "spring.sql.init.mode=never")
class SmarthomeApplicationTests {

     @Autowired private MockMvc mockMvc;
    @Autowired private Houserepo houseRepository;
    @Autowired private Roomrepo roomRepository;
    @Autowired private Devicerepo deviceRepository;
     @Autowired private Inventoryrepo inventoryRepository;
    @Autowired private Userrepo userRepository;

    // using this to force hibernate to update the db immediately
    @Autowired private EntityManager entityManager;

    @Test
    void testListRoomsAndDevices_Integration() throws Exception {
        // create a test user first
        User user = User.builder().name("Test Admin").email("test@admin.com").build();
       userRepository.save(user);

        // make a house for that user
         House house = House.builder().name("Integration House").admin(user).build();
        houseRepository.save(house);

        // add a room to the house
        Room room = Room.builder().name("Gaming Room").house(house).build();
        roomRepository.save(room);

        // create the inventory record for the hardware
         Inventory inv = Inventory.builder()
                .kickstonId("FFFFFF")
                 .deviceUsername("user")
                .devicePassword("pass")
               .manufactureDateTime(LocalDateTime.now())
                .build();
        inventoryRepository.save(inv);

        //  link the device to the room and inventory
      Device device = Device.builder()
                .customName("Pro Gaming PC")
                 .room(room)
                .inventoryDetails(inv)
                .build();
          deviceRepository.save(device);

        entityManager.flush();
       entityManager.clear();

        mockMvc.perform(get("/api/houses/" + house.getId() + "/rooms"))
                .andExpect(status().isOk())
                 .andExpect(jsonPath("$[0].name").value("Gaming Room"))
                 .andExpect(jsonPath("$[0].devices[0].customName").value("Pro Gaming PC"));
    }
}