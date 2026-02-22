package com.example.user.config;

import com.example.user.domain.User;
import com.example.user.repository.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class SampleDataLoader {

    @Bean
    CommandLineRunner loadSampleUsers(UserRepository userRepository) {
        return args -> {
            if (userRepository.count() > 0) return;
            userRepository.save(user("Alice", "alice@example.com"));
            userRepository.save(user("Bob", "bob@example.com"));
            userRepository.save(user("Carol", "carol@example.com"));
        };
    }

    private static User user(String name, String email) {
        User u = new User();
        u.setName(name);
        u.setEmail(email);
        return u;
    }
}
