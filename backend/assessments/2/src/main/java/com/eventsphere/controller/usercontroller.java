package com.eventsphere.controller;

import com.eventsphere.dto.reserve;
import com.eventsphere.entity.booking;
import com.eventsphere.repo.bookingrepo;
import com.eventsphere.repo.reserverepo;
import com.eventsphere.repo.userrepo;
import com.eventsphere.service.bookingservice;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class usercontroller {

private final bookingservice bookingservice;
private final reserverepo reserverepo;
private final bookingrepo bookingrepo;
private final userrepo userrepo;

private static final Logger log = LoggerFactory.getLogger(usercontroller.class);

private Long get_Id() {
        // i did this so i can pull the name out of the token and then find the id
        String User_Name = SecurityContextHolder.getContext().getAuthentication().getName();
return userrepo.findByUsername(User_Name).get().getId();
    }

    @PostMapping("/reservations")
public ResponseEntity<?> makereservation(@RequestBody reserve dto) {
log.info("user reserving tickets...");

    Long Id_val = dto.getEventId();
    int count_val = dto.getTicketCount();

        // calling the service to do the logic
    Object Final_Res = bookingservice.createReservation(Id_val, count_val);
    return ResponseEntity.ok(Final_Res);
    }

    @GetMapping("/reservations")
public ResponseEntity<List<com.eventsphere.entity.reserve>> getmyreservations() {
    Long My_Id = get_Id();
    List<com.eventsphere.entity.reserve> My_List = reserverepo.findByUserId(My_Id);
    return ResponseEntity.ok(My_List);
    }

    @PostMapping("/bookings/{resid}")
public ResponseEntity<booking> confirmmybooking(@PathVariable Long resid) {
        log.info("confirming res id: {}", resid);
        // i just return the whole booking object here once it's finished
booking Finished_Booking = bookingservice.confirmBooking(resid);
return ResponseEntity.ok(Finished_Booking);
    }
}