package com.meowmatch.meowmatch.service;

import com.meowmatch.meowmatch.models.Cat;
import com.meowmatch.meowmatch.models.SwipeState;
import com.meowmatch.meowmatch.models.conversations.ChatMessage;
import com.meowmatch.meowmatch.models.conversations.Conversation;
import com.meowmatch.meowmatch.models.match.Match;
import com.meowmatch.meowmatch.repository.CatRepository;
import com.meowmatch.meowmatch.repository.SwipeStateRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class SwipeStateService {
    private final SwipeStateRepository swipeStateRepository;
    private final MatchService matchService;
    private final CatService catService;

    public SwipeStateService(SwipeStateRepository swipeStateRepository, MatchService matchService, CatService catService) {
        this.swipeStateRepository = swipeStateRepository;
        this.matchService = matchService;
        this.catService = catService;
    }


    public boolean isMatched(String userId, String requestedId) {
        SwipeState swipeState = swipeStateRepository.findById(userId).orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Not matched yet"));
        return swipeState.getLikedCats().contains(requestedId);
    }

    public Match createBasicMatch(String userId, String requestedCatId) {
        SwipeState userSwipeState = findSwipeStateOrCreate(userId);
        userSwipeState.like(requestedCatId);
        swipeStateRepository.save(userSwipeState);

        return matchService.createBasicMatch(userId, requestedCatId);
    }

    public Cat nextProfile(String userId) {

        // user exist
        catService.findById(userId);

        // Get or create swipe state
        SwipeState state = findSwipeStateOrCreate(userId);
        //  Build excluded set: me + liked + disliked + already matched targets
        var excluded = new HashSet<String>();
        excluded.add(userId);
        excluded.addAll(state.getLikedCats());
        excluded.addAll(state.getdislikedCats());
        state.getMatches().forEach(m -> {
            if (m != null) excluded.add(m);
        });

        // unique and new candidates

        List<Cat> candidates = catService.findAll().stream()
                .filter(c -> c != null && c.getId() != null)
                .filter(c -> !excluded.contains(c.getId()))
                .toList();

        if (candidates.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No more profiles to match :(");
        }

        // Pick random
        int idx = ThreadLocalRandom.current().nextInt(candidates.size());
        return candidates.get(idx);
    }

    public ResponseEntity<String> dislikeProfile( String userId,String requestedCatId) {

        Cat user = catService.findById(userId);
        String dislikedProfileId=catService.findById(requestedCatId).getId();
        SwipeState swipeState=findSwipeStateOrCreate(userId);
        swipeState.dislike(requestedCatId);
        swipeStateRepository.save(swipeState);

        return ResponseEntity.ok("disliked cat ");
    }
    private SwipeState findSwipeStateOrCreate(String userId){
        return swipeStateRepository.findById(userId)
                .orElseGet(() -> swipeStateRepository.save(new SwipeState(userId)));
    }
}
