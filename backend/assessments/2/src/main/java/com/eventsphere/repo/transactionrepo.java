package com.eventsphere.repo;

import com.eventsphere.entity.transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface transactionrepo extends JpaRepository<transaction, Long> { Optional<transaction> findByTransactionId(String tid);

}