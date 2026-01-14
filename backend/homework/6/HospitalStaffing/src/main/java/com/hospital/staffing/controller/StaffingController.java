package com.hospital.staffing.controller;

import com.hospital.staffing.entity.Shift;
import com.hospital.staffing.entity.User;
import com.hospital.staffing.service.StaffingService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class StaffingController {

    private final StaffingService service;

    // Exercise 1 Task 3: Save Endpoint
    @PostMapping("/users")
    public User createUser(@RequestBody User user) {
        return service.saveUser(user);
    }

    // Exercise 2 Task 1: Paginated Users
    // Supports page (default 0) and size (default 50)
    @GetMapping("/users")
    public Page<User> getUsers(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "50") int size) {
        return service.getAllUsers(page, size);
    }

    // Exercise 2 Task 2: Top 3 Shifts Lookup
    @GetMapping("/shifts/new-year")
    public List<Shift> getNewYearShifts() {
        return service.getNewYearShifts();
    }
}