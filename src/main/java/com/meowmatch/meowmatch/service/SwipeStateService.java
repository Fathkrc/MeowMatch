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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

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

//    public boolean isLiked(String userId,String targetedUserId ){
//
//        SwipeState swipeState=swipeStateRepository.findById(userId).orElseThrow() ; // throws if not found
//        return swipeState.getdislikedCats().
//    }

    public boolean isMatched(String userId,String requestedId) {
        SwipeState swipeState=swipeStateRepository.findById(userId).orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND,"Not matched yet"));
        return swipeState.getLikedCats().contains(requestedId);
    }

    public Match createBasicMatch(String requestedCatId, String userId) {

        return matchService.createBasicMatch(userId,requestedCatId);
    }
// todo: ADD NEXT MECHANICS
    public Cat nextProfile(String userId) {
        return catService.getRandomCat(userId);
    }

    public ResponseEntity<String> dislikeProfile(String requestedCatId, String userId) {

        Cat user=catService.findById(requestedCatId);

        return ResponseEntity.ok("disliked cat ");
    }
}
