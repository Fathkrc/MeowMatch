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

@Service
public class SwipeStateService {
    private final SwipeStateRepository swipeStateRepository;
    private final MatchService matchService;
    private final CatService catService;

    //todo: remove this cat repo
    private final CatRepository catRepository;

    public SwipeStateService(SwipeStateRepository swipeStateRepository, MatchService matchService, CatService catService, CatRepository catRepository) {
        this.swipeStateRepository = swipeStateRepository;
        this.matchService = matchService;
        this.catService = catService;
        this.catRepository = catRepository;
    }


    public boolean isMatched(String userId,String requestedId) {
        SwipeState swipeState=swipeStateRepository.findById(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not matched yet"));
        return swipeState.getLikedCats().contains(requestedId);
    }

    public Match createBasicMatch( String userId,String requestedCatId) {
        SwipeState userSwipeState=swipeStateRepository
                .findById(userId)
                .orElse( swipeStateRepository.save(new SwipeState(userId)));
        userSwipeState.like(requestedCatId);
        swipeStateRepository.save(userSwipeState);
        return matchService.createBasicMatch(userId,requestedCatId);
    }
// todo: ADD NEXT MECHANICS
    public Cat nextProfile(String userId) {

        // user exist
        catService.getById(userId);

        // Get or create swipe state
        SwipeState state = swipeStateRepository.findById(userId)
                .orElseGet(() -> swipeStateRepository.save(new SwipeState(userId)));

        //  Build excluded set: me + liked + disliked + already matched targets
        var excluded = new HashSet<String>();
        excluded.add(userId);
        excluded.addAll(state.getLikedCats());
        excluded.addAll(state.getdislikedCats());
        state.getMatches().forEach(m -> {
            if (m != null ) excluded.add(m);
        });

        // unique and new candidates

        List<Cat> candidates = catRepository.findAll().stream()
                .filter(c -> c != null && c.id() != null)
                .filter(c -> !excluded.contains(c.id()))
                .toList();

        if (candidates.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No more profiles to match");
        }

        // Pick random
        int idx = java.util.concurrent.ThreadLocalRandom.current().nextInt(candidates.size());
        return candidates.get(idx);
    }

    public ResponseEntity<String> dislikeProfile(String requestedCatId, String userId) {

        Cat user=catService.findById(requestedCatId);

        return ResponseEntity.ok("disliked cat ");
    }
}
