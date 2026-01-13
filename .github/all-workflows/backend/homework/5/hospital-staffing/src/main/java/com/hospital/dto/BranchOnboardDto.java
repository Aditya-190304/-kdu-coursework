package com.hospital.dto;

import com.hospital.model.ShiftType;
import com.hospital.model.User;
import java.util.List;
import java.util.UUID;

public record BranchOnboardDto(
        UUID tenantId,
        List<ShiftType> shiftTypes,
        List<User> staff,
        boolean triggerFailure // For testing rollback
) {}