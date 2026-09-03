package com.meowmatch.meowmatch.controller;

import com.meowmatch.meowmatch.models.Cat;
import com.meowmatch.meowmatch.models.SwipeState;
import com.meowmatch.meowmatch.models.match.Match;
import com.meowmatch.meowmatch.repository.SwipeStateRepository;
import com.meowmatch.meowmatch.service.CatService;
import com.meowmatch.meowmatch.service.SwipeStateService;
import com.meowmatch.meowmatch.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/home")
public class SwipeController {

    private final SwipeStateService swipeService;
    private final SwipeStateRepository swipeStateRepository;
    private final UserService userService;
    private final CatService catService;

    public SwipeController(SwipeStateService swipeStateService, SwipeStateRepository swipeStateRepository, UserService userService, CatService catService) {
        this.swipeService = swipeStateService;
        this.swipeStateRepository = swipeStateRepository;
        this.userService = userService;
        this.catService = catService;
    }

    @GetMapping("/me/next")
    public Cat nextProfileToSwipe(Authentication authentication) {
        String username = (authentication != null) ? String.valueOf(authentication.getPrincipal()) : null;
        if (username == null || username.isBlank() || "anonymousUser".equals(username)) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.UNAUTHORIZED);
        }
        String userId = userService.getMe(username).getId();
        return swipeService.nextProfile(catIdFromUserId(userId));
    }

    @PostMapping("/me/like/{requestedCatId}")
    public ResponseEntity<Match> like(Authentication authentication, @PathVariable String requestedCatId) {
        String username = (authentication != null) ? String.valueOf(authentication.getPrincipal()) : null;
        if (username == null || username.isBlank() || "anonymousUser".equals(username)) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.UNAUTHORIZED);
        }
        String userId = userService.getMe(username).getId();
        return ResponseEntity.ok(swipeService.createBasicMatch(catIdFromUserId(userId), requestedCatId));
    }

    @PostMapping("/me/dislike/{requestedCatId}")
    public ResponseEntity<String> dislike(Authentication authentication, @PathVariable String requestedCatId) {
        String username = (authentication != null) ? String.valueOf(authentication.getPrincipal()) : null;
        if (username == null || username.isBlank() || "anonymousUser".equals(username)) {
            throw new org.springframework.web.server.ResponseStatusException(org.springframework.http.HttpStatus.UNAUTHORIZED);
        }
        String userId = userService.getMe(username).getId();
        return swipeService.dislikeProfile(requestedCatId, catIdFromUserId(userId));
    }

    @GetMapping("/{userId}/next")
    public Cat nextProfileToSwipe(@PathVariable String userId){
       return swipeService.nextProfile(userId);
    }


    @PostMapping("/{userId}/like/{requestedCatId}")
    public ResponseEntity<Match> like(@PathVariable String userId,@PathVariable String requestedCatId) {
        return ResponseEntity.ok(swipeService.createBasicMatch(userId,
                requestedCatId));
    }
    @PostMapping("/{userId}/dislike/{requestedCatId}")
    public ResponseEntity<String> dislike(@PathVariable String requestedCatId,@PathVariable String userId) {
        return swipeService.dislikeProfile(requestedCatId, userId);
    }
    @GetMapping("/allStates")
    public List<SwipeState> getAllSwipeStates(){
        return swipeStateRepository.findAll();
    }

    private String catIdFromUserId(String userId) {
        // Cat.userId stores User.id
        return catService.findByUserId(userId).getId();
    }
}
