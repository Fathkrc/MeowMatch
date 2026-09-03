package com.meowmatch.meowmatch.controller;

import com.meowmatch.meowmatch.models.Cat;
import com.meowmatch.meowmatch.models.SwipeState;
import com.meowmatch.meowmatch.models.match.Match;
import com.meowmatch.meowmatch.repository.SwipeStateRepository;
import com.meowmatch.meowmatch.service.CurrentUserService;
import com.meowmatch.meowmatch.service.SwipeStateService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/home")
public class SwipeController {

    private final SwipeStateService swipeService;
    private final SwipeStateRepository swipeStateRepository;
    private final CurrentUserService currentUserService;

    public SwipeController(
            SwipeStateService swipeStateService,
            SwipeStateRepository swipeStateRepository,
            CurrentUserService currentUserService
    ) {
        this.swipeService = swipeStateService;
        this.swipeStateRepository = swipeStateRepository;
        this.currentUserService = currentUserService;
    }

    @GetMapping("/me/next")
    public Cat nextProfile(Authentication authentication) {
        String userCatId = currentUserService.requireCatId(authentication);
        return swipeService.nextProfile(userCatId);
    }

    @PostMapping("/me/like/{targetCatId}")
    public ResponseEntity<Match> like(Authentication authentication, @PathVariable String targetCatId) {
        String userCatId = currentUserService.requireCatId(authentication);
        return ResponseEntity.ok(swipeService.likeCat(userCatId, targetCatId));
    }

    @PostMapping("/me/dislike/{targetCatId}")
    public ResponseEntity<String> dislike(Authentication authentication, @PathVariable String targetCatId) {
        String userCatId = currentUserService.requireCatId(authentication);
        return swipeService.dislikeCat(userCatId, targetCatId);
    }

    @GetMapping("/allStates")
    public List<SwipeState> getAllSwipeStates() {
        return swipeStateRepository.findAll();
    }
}
