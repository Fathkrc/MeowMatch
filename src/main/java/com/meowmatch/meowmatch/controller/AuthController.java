package com.meowmatch.meowmatch.controller;

import com.meowmatch.meowmatch.models.dto.AuthResponse;
import com.meowmatch.meowmatch.models.dto.LoginRequest;
import com.meowmatch.meowmatch.models.dto.RegisterRequest;
import com.meowmatch.meowmatch.models.dto.UserResponse;
import com.meowmatch.meowmatch.service.UserService;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@Valid @RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.loginRequest(loginRequest));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthResponse> register(@Valid @RequestBody RegisterRequest registerRequest) {
        return userService.registerRequest(registerRequest);
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(Authentication authentication) {
        String username = (authentication != null) ? String.valueOf(authentication.getPrincipal()) : null;
        if (username == null || username.isBlank() || "anonymousUser".equals(username)) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(userService.getMe(username));
    }
}
