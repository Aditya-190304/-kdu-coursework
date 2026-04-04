package com.hospital.staffing.repo;

import com.hospital.staffing.entity.ShiftType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ShiftTypeRepo extends JpaRepository<ShiftType, Long> {
}