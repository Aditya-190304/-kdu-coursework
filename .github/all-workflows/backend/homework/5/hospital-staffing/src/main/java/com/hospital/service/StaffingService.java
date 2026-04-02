package com.hospital.service;

import com.hospital.dto.BranchOnboardDto;
import com.hospital.dto.UserDto;
import com.hospital.model.ShiftType;
import com.hospital.model.User;
import com.hospital.repository.StaffingRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
public class StaffingService {

    private static final Logger logger = LoggerFactory.getLogger(StaffingService.class);
    private final StaffingRepository repository;

    // Constructor Injection
    public StaffingService(StaffingRepository repository) {
        this.repository = repository;
    }

    // --- Exercise 1 Handlers ---

    public void createUser(UserDto userDto) {
        User user = User.builder()
                .id(UUID.randomUUID())
                .username(userDto.username())
                .loggedIn(false)
                .timezone(userDto.timezone() != null ? userDto.timezone() : "UTC")
                .tenantId(userDto.tenantId())
                .build();
        repository.saveUser(user);
    }

    public List<User> getUsersByTenant(UUID tenantId) {
        return repository.findUsersByTenant(tenantId);
    }

    public void updateUserTimezone(UUID userId, String timezone) {
        int rows = repository.updateUserTimezone(userId, timezone);
        if (rows == 0) {
            throw new IllegalArgumentException("User not found with ID: " + userId);
        }
    }

    public List<User> getUsersPaginated(int page, int size, String sortDir) {
        int offset = page * size;
        return repository.findAllUsers(size, offset, sortDir);
    }

    // --- Exercise 2: "All-or-Nothing" Transaction ---

    @Transactional(rollbackFor = Exception.class) //
    public void onboardBranch(BranchOnboardDto onboardDto) {
        logger.info("Starting batch onboarding for Tenant: {}", onboardDto.tenantId());

        // 1. Save Shift Types
        if (onboardDto.shiftTypes() != null) {
            for (ShiftType type : onboardDto.shiftTypes()) {
                type.setTenantId(onboardDto.tenantId());
                // Ensure ID exists
                if (type.getId() == null) type.setId(UUID.randomUUID());
                repository.saveShiftType(type);
            }
        }

        // 2. Save Users
        if (onboardDto.staff() != null) {
            for (User user : onboardDto.staff()) {
                user.setTenantId(onboardDto.tenantId());
                if (user.getId() == null) user.setId(UUID.randomUUID());
                repository.saveUser(user);
            }
        }

        // 3. Failure Simulation
        if (onboardDto.triggerFailure()) {
            logger.error("Simulating failure to trigger rollback...");
            throw new RuntimeException("Simulated Failure: Rolling back transaction!");
            // This exception triggers the rollback logic
        }

        logger.info("Batch onboarding completed successfully.");
    }
}