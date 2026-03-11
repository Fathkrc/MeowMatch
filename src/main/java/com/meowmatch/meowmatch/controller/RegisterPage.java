package com.meowmatch.meowmatch.controller;

import com.meowmatch.meowmatch.models.dto.RegisterRequest;
import com.meowmatch.meowmatch.models.dto.UserResponse;
import com.meowmatch.meowmatch.models.user.User;
import com.meowmatch.meowmatch.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth/register")
public class RegisterPage {
    private final UserService userService;

    public RegisterPage(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public ResponseEntity<UserResponse> register(@RequestBody RegisterRequest registerRequest) {
        return userService.registerRequest(registerRequest);
    }
}
