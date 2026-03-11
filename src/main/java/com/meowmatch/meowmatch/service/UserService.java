package com.meowmatch.meowmatch.service;

import com.meowmatch.meowmatch.models.dto.LoginRequest;
import com.meowmatch.meowmatch.models.dto.RegisterRequest;
import com.meowmatch.meowmatch.models.dto.UserResponse;
import com.meowmatch.meowmatch.models.user.User;
import com.meowmatch.meowmatch.repository.UserRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.NoSuchElementException;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    public UserService(UserRepository userRepository,  PasswordEncoder passwordEncoder) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public String loginRequest(LoginRequest loginRequest) {
        User user = userRepository.findByUsername(loginRequest.getUsername())
                .orElseThrow(() -> new NoSuchElementException("User not found"));

        if (!passwordEncoder.matches(loginRequest.getPassword(), user.getPasswordHash())) {
            throw new IllegalArgumentException("Invalid password");
        }

        return "Login successful";
    }

    public ResponseEntity<UserResponse> registerRequest(RegisterRequest registerRequest) {
        if (userRepository.existsByUsername(registerRequest.getUsername())) {
            throw new IllegalArgumentException("Username already exists");
        }

        User user = new User();
        user.setUsername(registerRequest.getUsername());
        user.setPasswordHash(passwordEncoder.encode(registerRequest.getPassword()));

        User savedUser = userRepository.save(user);

        UserResponse response = new UserResponse(savedUser.getId(), savedUser.getUsername());
        return ResponseEntity.ok(response);
    }
}