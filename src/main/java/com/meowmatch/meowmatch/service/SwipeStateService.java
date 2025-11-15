package com.meowmatch.meowmatch.service;

import com.meowmatch.meowmatch.models.Cat;
import com.meowmatch.meowmatch.models.SwipeState;
import com.meowmatch.meowmatch.repository.SwipeStateRepository;
import org.springframework.stereotype.Service;

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

    public boolean isLiked(String userId,String targetedUserId ){

        SwipeState swipeState=swipeStateRepository.findById(userId).orElseThrow() ; // throws if not found
        return swipeState.dislike(targetedUserId);
    }

    public boolean isThisMatchedProfile(String s) {

    }
}
