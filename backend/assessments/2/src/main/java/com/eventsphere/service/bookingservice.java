package com.eventsphere.service;

import com.eventsphere.entity.*;
import com.eventsphere.repo.*;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class bookingservice {

final eventrepo eventRepo;
final reserverepo reserveRepo;
private final bookingrepo bookingRepo;
private final userrepo userRepo;
private final transactionrepo transRepo; // i added this to save the phase 2 transaction stuff

private static final Logger log = LoggerFactory.getLogger(bookingservice.class);

private user get_User() {
        String name = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepo.findByUsername(name).orElseThrow(() -> new RuntimeException("user not in db"));
}

public event createevent(event e) {
    return eventRepo.save(e);
    }

public event updateeventtickets(Long id, int count) {
    event E = eventRepo.findById(id).orElseThrow();
E.setTicketCount(count);
return eventRepo.save(E);
    }

    @Transactional
public reserve createReservation(Long eventid, int count) {
log.info("Phase 2: locking the row so no one else can grab it");

        // swapped findById for the lock version so we dont get race conditions
event eve_obj = eventRepo.findByIdWithLock(eventid).orElseThrow();

if (eve_obj.getTicketCount() < count) {
// i put this check here after the lock so we are 100% sure about the count
throw new RuntimeException("sorry, someone just bought the last tickets!");
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

    @Transactional
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

        // i did this with a random uuid so every booking has a unique tracking id
        transaction T = new transaction();
        T.setTransactionId(UUID.randomUUID().toString());
        T.setTransactionDate(LocalDateTime.now());

        // saving the transaction record first then linking it
        transaction savedT = transRepo.save(T);
        Finished_Booking.setTransaction(savedT);

        return bookingRepo.save(Finished_Booking);
    }
}