package com.hospital.staffing.service;

import com.hospital.staffing.entity.Shift;
import com.hospital.staffing.entity.User;
import com.hospital.staffing.repo.ShiftRepo;
import com.hospital.staffing.repo.UserRepo;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor // Constructor Injection for SonarQube compliance
public class StaffingService {

    private final UserRepo userRepo;
    private final ShiftRepo shiftRepo;

    // Exercise 2 Task 1: Controlled Pagination with Validation
    public Page<User> getAllUsers(int page, int size) {
        // Constraints: Min 1, Max 50
        if (size < 1) {
            throw new IllegalArgumentException("Page size must be at least 1");
        }
        if (size > 50) {
            throw new IllegalArgumentException("Page size cannot exceed 50");
        }

        return userRepo.findAll(PageRequest.of(page, size));
    }

    // Exercise 2 Task 2: "New Year" Search Logic
    public List<Shift> getNewYearShifts() {
        LocalDate start = LocalDate.of(2023, 1, 1);
        LocalDate end = LocalDate.of(2023, 1, 25);
        return shiftRepo.findTop3ByStartDateGreaterThanEqualAndEndDateLessThanEqualOrderByShiftNameAsc(start, end);
    }

    // Helper to save user (used by controller)
    public User saveUser(User user) {
        return userRepo.save(user);
    }
}