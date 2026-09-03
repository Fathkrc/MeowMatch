package com.meowmatch.meowmatch.controller;

import com.meowmatch.meowmatch.models.dto.UserResponse;
import com.meowmatch.meowmatch.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthController {
    private final UserService userService;

    public AuthController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/me")
    public ResponseEntity<UserResponse> me(Authentication authentication) {
        // JwtAuthenticationFilter sets principal to username
        String username = (authentication != null) ? String.valueOf(authentication.getPrincipal()) : null;
        if (username == null || username.isBlank() || "anonymousUser".equals(username)) {
            return ResponseEntity.status(401).build();
        }
        return ResponseEntity.ok(userService.getMe(username));
    }
}
