package com.meowmatch.meowmatch.controller;

import com.meowmatch.meowmatch.models.dto.LoginRequest;
import com.meowmatch.meowmatch.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/login")
public class LoginPage {
    private final UserService userService;

    public LoginPage(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<String> login(@RequestBody LoginRequest loginRequest) {
        return ResponseEntity.ok(userService.loginRequest(loginRequest));
    }
}
