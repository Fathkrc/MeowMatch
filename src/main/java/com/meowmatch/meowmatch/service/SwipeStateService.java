package com.meowmatch.meowmatch.service;

import com.meowmatch.meowmatch.models.Cat;
import com.meowmatch.meowmatch.models.SwipeState;
import com.meowmatch.meowmatch.models.match.Match;
import com.meowmatch.meowmatch.repository.SwipeStateRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.HashSet;
import java.util.List;
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

    public Match likeCat(String userCatId, String targetCatId) {
        SwipeState swipeState = findSwipeStateOrCreate(userCatId);
        swipeState.like(targetCatId);
        swipeStateRepository.save(swipeState);
        return matchService.createMatch(userCatId, targetCatId);
    }

    public Cat nextProfile(String userCatId) {
        catService.findById(userCatId);

        SwipeState state = findSwipeStateOrCreate(userCatId);
        var excluded = new HashSet<String>();
        excluded.add(userCatId);
        excluded.addAll(state.getLikedCats());
        excluded.addAll(state.getdislikedCats());
        state.getMatches().forEach(m -> {
            if (m != null) excluded.add(m);
        });

        List<Cat> candidates = catService.findAll().stream()
                .filter(c -> c != null && c.getId() != null)
                .filter(c -> !excluded.contains(c.getId()))
                .toList();

        if (candidates.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NO_CONTENT, "No more profiles to match :(");
        }

        int idx = ThreadLocalRandom.current().nextInt(candidates.size());
        return candidates.get(idx);
    }

    public ResponseEntity<String> dislikeCat(String userCatId, String targetCatId) {
        catService.findById(userCatId);
        catService.findById(targetCatId);

        SwipeState swipeState = findSwipeStateOrCreate(userCatId);
        swipeState.dislike(targetCatId);
        swipeStateRepository.save(swipeState);

        return ResponseEntity.ok("disliked cat");
    }

    private SwipeState findSwipeStateOrCreate(String userCatId) {
        return swipeStateRepository.findById(userCatId)
                .orElseGet(() -> swipeStateRepository.save(new SwipeState(userCatId)));
    }
}
