package com.hospital.controller;

import com.hospital.dto.BranchOnboardDto;
import com.hospital.dto.UserDto;
import com.hospital.model.User;
import com.hospital.service.StaffingService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/staffing")
public class StaffingController {

    private final StaffingService service;

    public StaffingController(StaffingService service) {
        this.service = service;
    }

    // --- Exercise 1: Direct Entry ---

    @PostMapping("/users")
    public ResponseEntity<String> createUser(@Valid @RequestBody UserDto userDto) {
        service.createUser(userDto);
        return new ResponseEntity<>("User created successfully", HttpStatus.CREATED);
    }

    @GetMapping("/users/{tenantId}") //
    public ResponseEntity<List<User>> getUsersByTenant(@PathVariable UUID tenantId) {
        return ResponseEntity.ok(service.getUsersByTenant(tenantId));
    }

    @PatchMapping("/users/{userId}/timezone") //
    public ResponseEntity<String> updateTimezone(@PathVariable UUID userId, @RequestParam String timezone) {
        service.updateUserTimezone(userId, timezone);
        return ResponseEntity.ok("Timezone updated");
    }

    // --- Exercise 2: Transactional Batch ---

    @PostMapping("/onboard")
    public ResponseEntity<String> onboardBranch(@RequestBody BranchOnboardDto onboardDto) {
        service.onboardBranch(onboardDto);
        return new ResponseEntity<>("Branch onboarded successfully", HttpStatus.CREATED);
    }

    // --- Exercise 3: Pagination & Sorting ---

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size, //
            @RequestParam(defaultValue = "asc") String sortDir //
    ) {
        return ResponseEntity.ok(service.getUsersPaginated(page, size, sortDir));
    }
}