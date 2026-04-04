package com.hospital.staffing.repo;

import com.hospital.staffing.entity.Shift;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ShiftRepo extends JpaRepository<Shift, Long> {


    List<Shift> findTop3ByStartDateGreaterThanEqualAndEndDateLessThanEqualOrderByShiftNameAsc(
            LocalDate startDate, LocalDate endDate);
}