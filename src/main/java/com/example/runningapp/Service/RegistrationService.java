package com.example.runningapp.Service;

import com.example.runningapp.Controller.UserDto;
import com.example.runningapp.Entity.User;
import com.example.runningapp.Repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RegistrationService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public void register(UserDto userDto){
        User user = User.builder()
                .username(userDto.getName())
                .email(userDto.getEmail())
                .password(passwordEncoder.encode(userDto.getPassword()))
                .active(true)
                .roles("USER")
                .build();
        userRepository.save(user);
    }
}
