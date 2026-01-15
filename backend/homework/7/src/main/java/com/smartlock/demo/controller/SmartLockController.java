package com.smartlock.demo.controller;

import com.smartlock.demo.model.UnlockRequest;
import com.smartlock.demo.service.SmartLockService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/lock")
public class SmartLockController {

    private final SmartLockService smartLockService;

    public SmartLockController(SmartLockService smartLockService) {
        this.smartLockService = smartLockService;
    }

    // Endpoint to trigger the unlock process
    @PostMapping("/unlock")
    public String unlockDoor(@RequestBody UnlockRequest request) {
        smartLockService.unlock(request.getUser());
        return "Command received";
    }

    // Endpoint to test the battery check
    @GetMapping("/battery")
    public String checkBattery() {
        smartLockService.checkBattery();
        return "Battery checked";
    }
}