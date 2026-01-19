package com.eventsphere.service;

import com.eventsphere.entity.*;
import com.eventsphere.repo.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class bookingservice {


final eventrepo eventRepo;
final reserverepo reserveRepo;
private final bookingrepo bookingRepo;
private final userrepo userRepo;

private static final Logger log = LoggerFactory.getLogger(bookingservice.class);

private user get_User() { // snake_case method
    String name = SecurityContextHolder.getContext().getAuthentication().getName();
    return userRepo.findByUsername(name).orElseThrow(() -> new RuntimeException("user not in db"));
    }

public event createevent(event e) {
    return eventRepo.save(e);
    }

public event updateeventtickets(Long id, int count) {event E = eventRepo.findById(id).orElseThrow();
    E.setTicketCount(count);
    return eventRepo.save(E);
    }

public reserve createReservation(Long eventid, int count) {long id_VAL = eventid;
    event eve_obj = eventRepo.findById(id_VAL).orElseThrow();
        if (eve_obj.getTicketCount() < count) {
            throw new RuntimeException("not enough tickets left!!");
        }

    int OldCount = eve_obj.getTicketCount();
    int NewCount = OldCount - count;
        eve_obj.setTicketCount(NewCount);
        eventRepo.save(eve_obj);

        reserve r = new reserve();
        r.setEvent(eve_obj);
        r.setTicketsReserved(count);
        r.setUserId(get_User().getId());
        r.setStatus("active");
        r.setCreatedAt(LocalDateTime.now());

        return reserveRepo.save(r);
    }

    public booking confirmBooking(Long resid) {

        reserve res_item = reserveRepo.findById(resid).orElseThrow();

        if (!"active".equals(res_item.getStatus())) {
            throw new RuntimeException("can't book this!");
        }

        res_item.setStatus("booked");
        reserveRepo.save(res_item);

        booking Finished_Booking = new booking();
        Finished_Booking.setReservation(res_item);
        Finished_Booking.setUserId(get_User().getId());

        transaction T = new transaction();
        T.setTransactionId(java.util.UUID.randomUUID().toString());
        T.setTransactionDate(java.time.LocalDateTime.now());

        // Human way: save transaction first to be safe
        transaction savedT = transRepo.save(T);
        Finished_Booking.setTransaction(savedT);


        return bookingRepo.save(Finished_Booking);
    }
}