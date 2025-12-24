package com.meowmatch.meowmatch.service;

import com.meowmatch.meowmatch.models.Cat;
import com.meowmatch.meowmatch.models.conversations.ChatMessage;
import com.meowmatch.meowmatch.models.conversations.Conversation;
import com.meowmatch.meowmatch.models.match.Match;
import com.meowmatch.meowmatch.repository.CatRepository;
import com.meowmatch.meowmatch.repository.ConversationRepository;
import com.meowmatch.meowmatch.repository.MatchRepository;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final CatRepository catRepository;
    private final ConversationRepository conversationRepository;
    private final CatService catService;

    public MatchService(MatchRepository matchRepository, CatRepository catRepository, ConversationRepository conversationRepository, CatService catService) {
        this.matchRepository = matchRepository;
        this.catRepository = catRepository;
        this.conversationRepository = conversationRepository;
        this.catService = catService;
    }

    public Match createBasicMatch(String userId, String requestedCatId) {
        Cat user = catService.findById(userId);
        Cat matchedCat = catService.findById(requestedCatId);

        Conversation convo = new Conversation(UUID.randomUUID().toString(), userId, requestedCatId, new ArrayList<>());
        Conversation conversation = conversationRepository.save(convo);
        Match match1 = new Match(
                UUID.randomUUID().toString(),
                user.id(),
                matchedCat.id(),
                conversation.id());
        matchRepository.save(match1);
        return match1;
    }

    public List<Match> getAllCatMatches() {
        return matchRepository.findAll();
    }

    //its like messages section to see matches
    public List<Match> getUsersMatch(String userId) {
        return matchRepository.findAll()
                .stream()
                .filter(t -> t.userCatId().equals(userId))
                .toList();
    }
}
