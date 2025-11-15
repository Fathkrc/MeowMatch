package com.meowmatch.meowmatch.service;

import com.meowmatch.meowmatch.models.Cat;
import com.meowmatch.meowmatch.models.conversations.ChatMessage;
import com.meowmatch.meowmatch.models.conversations.Conversation;
import com.meowmatch.meowmatch.models.match.Match;
import com.meowmatch.meowmatch.repository.CatRepository;
import com.meowmatch.meowmatch.repository.ConversationRepository;
import com.meowmatch.meowmatch.repository.MatchRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.sql.Array;
import java.util.*;

@Service
public class MatchService {

    private final MatchRepository matchRepository;
    private final CatRepository catRepository;
    private final ConversationRepository conversationRepository;

    public MatchService(MatchRepository matchRepository, CatRepository catRepository, ConversationRepository conversationRepository) {
        this.matchRepository = matchRepository;
        this.catRepository = catRepository;
        this.conversationRepository = conversationRepository;
    }

    public Conversation createBasicMatch(String userId,String requestedCatId) {
        List<Cat> otherCats = catRepository.findAll()
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

    public List<Match> getAllCatMatches() {
        return matchRepository.findAll();
    }
}
