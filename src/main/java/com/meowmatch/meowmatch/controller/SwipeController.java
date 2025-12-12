package com.meowmatch.meowmatch.controller;

import com.meowmatch.meowmatch.models.Cat;
import com.meowmatch.meowmatch.models.conversations.Conversation;
import com.meowmatch.meowmatch.service.SwipeStateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController("home/discover/")
public class SwipeController {

    private final SwipeStateService swipeService;

    public SwipeController(SwipeStateService swipeStateService) {
        this.swipeService = swipeStateService;
    }

    @GetMapping
    public Cat nextProfileToSwipe(String userId){
       return swipeService.nextProfile(userId);
    }


    @PostMapping("{userId}/like/{requestedCatId}")
    public ResponseEntity<Conversation> matchRequest(@PathVariable String requestedCatId, String userId) {
        return ResponseEntity.ok(swipeService.createBasicMatch(requestedCatId,userId));
    }
    @PostMapping("{userId}/dislike/{requestedCatId}")
    public ResponseEntity<> dislikeProfile(@PathVariable String requestedCatId, String userId) {
        return ResponseEntity.ok(swipeService.dislikeProfile(requestedCatId,userId));
    }
}
