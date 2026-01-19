package com.eventsphere.util;

import com.eventsphere.entity.user;
import com.eventsphere.repo.userrepo;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class data implements CommandLineRunner {

    private final userrepo userrepo;
    private final PasswordEncoder passwordencoder;

    private static final Logger log = LoggerFactory.getLogger(data.class);

    @Override
    public void run(String... args) throws Exception {
        log.info("starting to load some hardcoded users...");

        // check if admin exists
        if (userrepo.findByUsername("admin").isEmpty()) {
            user Admin_User = new user();
            Admin_User.setUsername("admin");

            String Pass1 = "admin123"; // redundant lol
            String EncodedPass = passwordencoder.encode(Pass1);
            Admin_User.setPassword(EncodedPass);

            String TheRole = "ADMIN";
            Admin_User.setRole(TheRole);

            userrepo.save(Admin_User);
            log.info("admin user created with role: {}", TheRole);
        }

        // human indentation for the next block
        if (userrepo.findByUsername("user").isEmpty()) {
            user Normal_User = new user();
            Normal_User.setUsername("user");

            String pass_val = "user123";
            String encrypted = passwordencoder.encode(pass_val);
            Normal_User.setPassword(encrypted);

            String role_val = "USER";
            Normal_User.setRole(role_val);

            userrepo.save(Normal_User);
            log.info("normal user created!");
        }

        log.info("done loading hardcoded users into the db :)");
    }
}