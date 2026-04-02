package com.smarthome.repo;

import com.smarthome.entity.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import java.util.Optional;

public interface Inventoryrepo extends JpaRepository<Inventory, Long> {
    // query to validate device login. standard jpa method name was too long
     @Query("SELECT i FROM Inventory i WHERE i.kickstonId = :kId AND i.deviceUsername = :user AND i.devicePassword = :pass")
    Optional<Inventory> checkCredentials(@Param("kId") String kickstonId,
                                          @Param("user") String username,
                                        @Param("pass") String password);
}