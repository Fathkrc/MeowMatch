package com.meowmatch.meowmatch.controller;

import com.meowmatch.meowmatch.models.conversations.ChatMessage;
import com.meowmatch.meowmatch.models.conversations.Conversation;
import com.meowmatch.meowmatch.models.dto.CreateConversationRequest;
import com.meowmatch.meowmatch.repository.CatRepository;
import com.meowmatch.meowmatch.repository.ConversationRepository;
import com.meowmatch.meowmatch.service.ConversationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController
public class ConversationController {
    private final ConversationService conversationService;

    //TODO: these are temporary I will take business logic to service layer !!!!!!!!!!!!!
    private final ConversationRepository conversationRepository;
    private final CatRepository catRepository;
    //TODO: these are temporary I will take business logic to service layer !!!!!!!!!!!!!

    public ConversationController(ConversationService conversationService, ConversationRepository conversationRepository, CatRepository catRepository) {
        this.conversationService = conversationService;
        this.conversationRepository = conversationRepository;
        this.catRepository = catRepository;
    }
    // Create conversation request with userId
    @PostMapping("/conversations")
    public ResponseEntity<String> createNewConversation(@RequestBody CreateConversationRequest request){

        return ResponseEntity.ok(conversationService.createNewConversation(request));

    }
    // Get All conversations
    @GetMapping("/conversations")
    public List<Conversation> getAllConversations(){
        return conversationService.getAllConversation();
    }

//    //todo: Deleting Conversation this can wait !!
//    @DeleteMapping("/conversations")
//    public ResponseEntity<Void> deleteConversationById(@RequestBody String id){
//        conversationService.deleteById(id);
//        return ResponseEntity.noContent().build(); // 204 No Content
//
//    }
    // adding message to conversation
    // MOVE THIS TO SERVICE
    @PutMapping("/conversations/{conversationId}")
    public Conversation addMessageToConversation(
            @PathVariable String conversationId,@RequestBody ChatMessage chatMessage){

        // we are checking here if the cat exist with that authorId and if not we throw an error
        catRepository.findById(chatMessage.authorId()).orElseThrow(()->
                new ResponseStatusException(HttpStatus.NOT_FOUND,"Cat with that id is not found ")
        );

        // if that conversation not exist app throws an error
        Conversation conversation= conversationRepository.findById(conversationId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND
                        , "Unable to Find profile"));

        // I am creating a new chatMessage to save with the actual datetime because chatMessage is a record and immutable
         //TODO:: NEED TO VALIDATE AUTHOR OF A MESSAGE HAPPENED TO BE ONLY THE PROFILE ASSOCIATED WITH !!!!!!!
        ChatMessage messageWithTime= new ChatMessage(
                chatMessage.messageText(),
                chatMessage.authorId(),
                LocalDateTime.now()
        );
        conversation.messages().add(messageWithTime);
        conversationRepository.save(conversation);
        return conversation;
    }
}
