package com.meowmatch.meowmatch.service;

import com.meowmatch.meowmatch.models.Cat;
import com.meowmatch.meowmatch.models.SwipeState;
import com.meowmatch.meowmatch.models.conversations.ChatMessage;
import com.meowmatch.meowmatch.models.conversations.Conversation;
import com.meowmatch.meowmatch.models.match.Match;
import com.meowmatch.meowmatch.repository.SwipeStateRepository;
import org.springframework.http.HttpStatus;
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

    public SwipeStateService(SwipeStateRepository swipeStateRepository, MatchService matchService, CatService catService) {
        this.swipeStateRepository = swipeStateRepository;
        this.matchService = matchService;
        this.catService = catService;
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

    public Conversation createBasicMatch(String requestedCatId, String userId) {

        List<Cat> otherCats = catService.findAll()
                .stream()
                .filter(c -> !c.id().equals(userId))
                .toList();

        if (otherCats.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No other cats to match");
        }

        Optional<Cat> matchedCat = catRepository.findById(requestedCatId);

        Conversation convo = new Conversation(UUID.randomUUID().toString() ,userId, requestedCatId ,new ArrayList<ChatMessage>());
        Conversation conversation= conversationRepository.save(convo);
        // todo: why null ID on match??
        Match match1=new Match(UUID.randomUUID().toString(), matchedCat.orElseGet(null) ,conversation.id());
        matchRepository.save(match1);
        return conversation;
    }

    public Cat nextProfile(String userId) {
        return catService.getRandomCat(userId);
    }

    public void dislikeProfile(String requestedCatId, String userId) {

    }
}
