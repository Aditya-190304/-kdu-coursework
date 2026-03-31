package com.eventsphere.repo;
import com.eventsphere.entity.reserve;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface reserverepo extends JpaRepository<reserve, Long> { List<reserve> findByUserId(Long userId);
}