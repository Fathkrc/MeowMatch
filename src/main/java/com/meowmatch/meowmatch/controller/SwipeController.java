package com.meowmatch.meowmatch.controller;

import com.meowmatch.meowmatch.models.Cat;
import com.meowmatch.meowmatch.models.SwipeState;
import com.meowmatch.meowmatch.models.match.Match;
import com.meowmatch.meowmatch.repository.SwipeStateRepository;
import com.meowmatch.meowmatch.service.SwipeStateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("/home")
public class SwipeController {

    private final SwipeStateService swipeService;
    private final SwipeStateRepository swipeStateRepository;

    public SwipeController(SwipeStateService swipeStateService, SwipeStateRepository swipeStateRepository) {
        this.swipeService = swipeStateService;
        this.swipeStateRepository = swipeStateRepository;
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
}
