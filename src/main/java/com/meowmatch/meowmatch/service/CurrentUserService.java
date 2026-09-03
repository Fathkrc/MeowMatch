package com.meowmatch.meowmatch.service;

import com.meowmatch.meowmatch.models.Cat;
import com.meowmatch.meowmatch.models.dto.UserResponse;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

@Service
public class CurrentUserService {
    private final UserService userService;
    private final CatService catService;

    public CurrentUserService(UserService userService, CatService catService) {
        this.userService = userService;
        this.catService = catService;
    }

    public UserResponse requireUser(Authentication authentication) {
        String username = usernameFrom(authentication);
        return userService.getMe(username);
    }

    public Cat requireCat(Authentication authentication) {
        UserResponse user = requireUser(authentication);
        return catService.findByUserId(user.getId());
    }

    public String requireCatId(Authentication authentication) {
        return requireCat(authentication).getId();
    }

    private String usernameFrom(Authentication authentication) {
        if (authentication == null || authentication.getPrincipal() == null) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        String username = String.valueOf(authentication.getPrincipal());
        if (username.isBlank() || "anonymousUser".equals(username)) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        return username;
    }
}
