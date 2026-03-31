package com.eventsphere.repo;

import com.eventsphere.entity.user;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface userrepo extends JpaRepository<user, Long> {
    Optional<user> findByUsername(String username);
}