package com.eventsphere.controller;

import com.eventsphere.entity.event;
import com.eventsphere.repo.eventrepo;
import com.eventsphere.service.bookingservice;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class evecontroller {

    private final bookingservice bookingservice;
    private final eventrepo eventrepo;

    private static final Logger log = LoggerFactory.getLogger(evecontroller.class);

    @PostMapping("/admin/events")
    public ResponseEntity<event> addevent(@RequestBody event eveobj) {
        log.info("admin adding event...");

        String NameCheck = eveobj.getName();
        if (NameCheck == null || NameCheck.isEmpty()) {
            // added this check because a conference needs a name or it's confusing
            throw new RuntimeException("event name missing lol");
        }
        event Saved_Event = bookingservice.createevent(eveobj);
        return ResponseEntity.ok(Saved_Event);
    }

    @PutMapping("/admin/events/{id}")
    public ResponseEntity<event> updateevent(@PathVariable Long id, @RequestBody event e) {

        int New_Count = e.getTicketCount();

        if (New_Count < 0) {
            // prevent admin from putting -10 tickets because that makes no sense
            log.error("negative tickets!!");
            throw new RuntimeException("cannot have negative tickets lol");
        }

        event UpdatedObj = bookingservice.updateeventtickets(id, New_Count);
        return ResponseEntity.ok(UpdatedObj);
    }

    @GetMapping("/user/events")
    public ResponseEntity<Page<event>> getevents(@RequestParam(defaultValue = "0") int page,
                                                 @RequestParam(defaultValue = "10") int size) {
        // storing these in local variables so I can debug easily
        int P = page;
        int S = size;

        PageRequest reqObj = PageRequest.of(P, S);
        Page<event> Result_list = eventrepo.findAll(reqObj);

        return ResponseEntity.ok(Result_list);
    }
}