package com.smarthome.repo;

import com.smarthome.entity.Device;
import com.smarthome.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Devicerepo extends JpaRepository<Device, Long> {

boolean existsByInventoryDetails(Inventory inventory);
}