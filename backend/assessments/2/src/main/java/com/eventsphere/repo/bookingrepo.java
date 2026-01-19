package com.eventsphere.repo;
import com.eventsphere.entity.booking;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface bookingrepo extends JpaRepository<booking, Long> {


    List<booking> findByUserId(Long userId);
}
