package com.meowmatch.meowmatch.service;

import com.meowmatch.meowmatch.models.Cat;
import com.meowmatch.meowmatch.models.conversations.Conversation;
import com.meowmatch.meowmatch.models.match.Match;
import com.meowmatch.meowmatch.repository.MatchRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final ConversationService conversationService;
    private final CatService catService;

    public MatchService(MatchRepository matchRepository, ConversationService conversationService, CatService catService) {
        this.matchRepository = matchRepository;
        this.conversationService = conversationService;
        this.catService = catService;
    }

    public Match createMatch(String userCatId, String targetCatId) {
        Cat userCat = catService.findById(userCatId);
        Cat targetCat = catService.findById(targetCatId);

        Conversation convo = new Conversation(userCatId, targetCatId, new ArrayList<>());
        conversationService.saveConversation(convo);

        Match match = new Match(userCat.getId(), targetCat.getId(), convo.getId());
        return matchRepository.save(match);
    }

    public List<Match> getAllCatMatches() {
        return matchRepository.findAll();
    }

    public List<Match> getMatchesForCat(String userCatId) {
        return matchRepository.findAll()
                .stream()
                .filter(match -> match.getUserCatId().equals(userCatId))
                .toList();
    }
}
