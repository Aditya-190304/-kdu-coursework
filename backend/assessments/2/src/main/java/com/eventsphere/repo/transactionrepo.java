package com.eventsphere.repo;

import com.eventsphere.entity.transaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface transactionrepo extends JpaRepository<transaction, Long> {

    // i added this finder because we might need to search by the uuid string later
Optional<transaction> findByTransactionId(String tid);

}