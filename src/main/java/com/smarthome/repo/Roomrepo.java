package com.smarthome.repo;

import com.smarthome.entity.Room;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Roomrepo extends JpaRepository<Room, Long> {

     boolean existsByNameAndHouseId(String name, Long houseId);
}