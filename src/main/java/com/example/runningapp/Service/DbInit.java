package com.example.runningapp.Service;

import com.example.runningapp.Entity.User;
import com.example.runningapp.Repository.UserRepository;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
@RequiredArgsConstructor
public class DbInit implements CommandLineRunner {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Override
    public void run(String... args) throws Exception {

        List<User> users = userRepository.findAllByUsernameIn(List.of("user","admin"));

        if (users.isEmpty()) {
            List<User> newUsers = List.of();
            User user = User.builder()
                    .username("user")
                    .password(passwordEncoder.encode("user123"))
                    .email("user@user.pl")
                    .roles("USER")
                    .active(true)
                    .build();

            User admin = User.builder()
                    .username("admin")
                    .password(passwordEncoder.encode("admin123"))
                    .email("admin@admin.pl")
                    .roles("ADMIN")
                    .active(true)
                    .build();
            this.userRepository.saveAll(List.of(user, admin));
        }
    }
}
