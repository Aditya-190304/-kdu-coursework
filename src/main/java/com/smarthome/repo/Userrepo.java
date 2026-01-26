package com.smarthome.repo;
import com.smarthome.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
public interface Userrepo extends JpaRepository<User, Long> {}