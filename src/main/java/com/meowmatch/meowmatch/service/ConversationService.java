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
    private final OllamaService ollamaService;
    private final SwipeStateService swipeStateService;

    public ConversationService(ConversationRepository conversationRepository, CatRepository catRepository, @Lazy OllamaService ollamaService, SwipeStateService swipeStateService) {
        this.conversationRepository = conversationRepository;
        this.catRepository = catRepository;
        this.ollamaService = ollamaService;
        this.swipeStateService = swipeStateService;
    }
// todo : move the checking cats existence as a helper method
    public String createNewConversation(CreateConversationRequest request) {
        getAuthorWithAuthorIdOrThrow(request.userId());
        swipeStateService.isMatched(request.userId(), request.targetedProfileId());
        Conversation conversation = new Conversation(UUID.randomUUID().toString(),
                request.userId(),
                request.targetedProfileId(),
                new ArrayList<>());
        conversationRepository.save(conversation);
        return conversation.profileId() != null ? "conversation created " : "no profile id ";
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

        Cat conversationRequestedCat= getAuthorWithAuthorIdOrThrow(chatMessage.authorId());

        // if that conversation not exist app throws an error without stopping app
        Conversation conversation = getConversationWithConversationIdOrThrow(conversationId);

        // I am creating a new chatMessage to save with the actual datetime because chatMessage is a record and immutable
        //TODO:: NEED TO VALIDATE AUTHOR OF A MESSAGE HAPPENED TO BE ONLY THE PROFILE ASSOCIATED WITH !!!!!!!
        ChatMessage messageWithTime = new ChatMessage(
                chatMessage.messageText(),
                "a",
                LocalDateTime.now()
        );
        conversation.messages().add(messageWithTime);
        ChatMessage chatMessageFromOllama= ollamaService.responseToAddedMessageToConversation(conversationId,conversationRequestedCat,messageWithTime);
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
}
