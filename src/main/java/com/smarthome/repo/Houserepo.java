package com.smarthome.repo;
import com.smarthome.entity.House;
import org.springframework.data.jpa.repository.JpaRepository;


public interface Houserepo extends JpaRepository<House, Long> {}