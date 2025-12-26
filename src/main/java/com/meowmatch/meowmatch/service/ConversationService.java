package com.meowmatch.meowmatch.service;

import com.meowmatch.meowmatch.models.Cat;
import com.meowmatch.meowmatch.models.conversations.ChatMessage;
import com.meowmatch.meowmatch.models.conversations.Conversation;
import com.meowmatch.meowmatch.models.dto.CreateConversationRequest;
import com.meowmatch.meowmatch.repository.CatRepository;
import com.meowmatch.meowmatch.repository.ConversationRepository;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ConversationService {

    private final ConversationRepository conversationRepository;
    private final CatRepository catRepository;
    private final CatService catService;
    private final OllamaService ollamaService;

    public ConversationService(ConversationRepository conversationRepository, CatRepository catRepository, CatService catService, @Lazy OllamaService ollamaService) {
        this.conversationRepository = conversationRepository;
        this.catRepository = catRepository;
        this.catService = catService;
        this.ollamaService = ollamaService;
    }

// this should be admin
    public List<Conversation> getAllConversation() {
        return conversationRepository.findAll();
    }

    public void deleteById(String id) {
        Conversation conversation = getConversationWithConversationIdOrThrow(id);
        conversationRepository.delete(conversation);
    }

    public Conversation getConversationsWithId(String conversationId) {
        return getConversationWithConversationIdOrThrow(conversationId);

    }

    public Conversation addMessageToExistingConversationService(String conversationId, ChatMessage chatMessage) {
        Conversation conversation = getConversationWithConversationIdOrThrow(conversationId);

        Cat conversationRequestedCat= catService.findById(chatMessage.targetedProfile());

        //TODO:: NEED TO VALIDATE AUTHOR OF A MESSAGE HAPPENED TO BE ONLY THE PROFILE ASSOCIATED WITH !!!!!!!
        ChatMessage messageWithTime = new ChatMessage(
                chatMessage.messageText(),
                chatMessage.authorId(),
                chatMessage.targetedProfile(),
                LocalDateTime.now()
        );
        conversation.messages().add(messageWithTime);
        ChatMessage chatMessageFromOllama= ollamaService.responseToAddedMessageToConversation(conversationId,messageWithTime);
        conversation.messages().add(chatMessageFromOllama);
        conversationRepository.save(conversation);
        return conversation;
    }

    // HELPERS
    private Cat getAuthorWithAuthorIdOrThrow(String authorId) {
        return catRepository.findById(authorId)
                .orElseThrow(() ->
                        new ResponseStatusException(HttpStatus.NOT_FOUND, "Cat with " + authorId + " id is not found")
                );
    }

    private Conversation getConversationWithConversationIdOrThrow(String conversationId) {
        return conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND
                        , "Unable to Find conversation with " + conversationId + " id"));
    }

    public List<Conversation> getConversationsUserHas(String catId) {
        return conversationRepository.findAll()
                .stream()
                .filter(t->t.profileId().equals(catId))
                .toList();
    }

    public void saveConversation(Conversation convo) {
        conversationRepository.save(convo);
    }
}
