package com.meowmatch.meowmatch.service;

import com.meowmatch.meowmatch.models.conversations.Conversation;
import com.meowmatch.meowmatch.models.dto.CreateConversationRequest;
import com.meowmatch.meowmatch.repository.CatRepository;
import com.meowmatch.meowmatch.repository.ConversationRepository;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final CatRepository catRepository;

    public ConversationService(ConversationRepository conversationRepository, CatRepository catRepository) {
        this.conversationRepository = conversationRepository;
        this.catRepository = catRepository;
    }

    public String createNewConversation(CreateConversationRequest request) {
        catRepository.findById(request.profileId())
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND));
        Conversation conversation=new Conversation(UUID.randomUUID().toString(),
                request.profileId(),
                new ArrayList<>());
        conversationRepository.save(conversation);
        return conversation.profileId()!=null ? "saved" : "no profile id ";
    }

    public List<Conversation> getAllConversation() {
        return conversationRepository.findAll();
    }

    public void deleteById(String id) {
        Conversation conversation = conversationRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND));
        conversationRepository.delete(conversation);
    }
}
