package com.eventsphere.repo;

import com.eventsphere.entity.event;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import java.util.Optional;

public interface eventrepo extends JpaRepository<event, Long> {
    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("SELECT e FROM event e WHERE e.id = :id")
    Optional<event> findByIdWithLock(Long id);
}