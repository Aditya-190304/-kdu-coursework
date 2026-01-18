package com.libraryweb;

import com.librarydomain.User;
import com.librarydomain.repo.UserRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableJpaAuditing
@SpringBootApplication(scanBasePackages = {
        "com.libraryweb",
        "com.libraryservice"
})
@EntityScan(basePackages = "com.librarydomain")
@EnableJpaRepositories(basePackages = "com.librarydomain.repo")
public class LibraryApplication {

    public static void main(String[] args) {
        SpringApplication.run(LibraryApplication.class, args);
    }

    // i injected these values so we don't hardcode secrets in the source code
    @Value("${app.admin.password}")
    private String adminPassword;

    @Value("${app.member.password}")
    private String memberPassword;

    @Bean
    CommandLineRunner init(UserRepository userRepo, PasswordEncoder encoder) {
        return args -> {
            // i checked if users exist to prevent overwriting or crashing on restart
            if (userRepo.count() == 0) {
                User lib = new User();
                lib.setUsername("admin");
                // i am using the injected value now
                lib.setPassword(encoder.encode(adminPassword));
                lib.setRole(User.Role.LIBRARIAN);
                userRepo.save(lib);

                User mem = new User();
                mem.setUsername("john");
                mem.setPassword(encoder.encode(memberPassword));
                mem.setRole(User.Role.MEMBER);
                userRepo.save(mem);
            }
        };
    }
}