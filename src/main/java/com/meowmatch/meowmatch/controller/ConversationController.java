package com.meowmatch.meowmatch.controller;

import com.meowmatch.meowmatch.models.conversations.ChatMessage;
import com.meowmatch.meowmatch.models.conversations.Conversation;
import com.meowmatch.meowmatch.models.dto.CreateConversationRequest;
import com.meowmatch.meowmatch.repository.CatRepository;
import com.meowmatch.meowmatch.repository.ConversationRepository;
import com.meowmatch.meowmatch.service.CatService;
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
    private final CatService catService;

    public ConversationController(ConversationService conversationService, ConversationRepository conversationRepository, CatRepository catRepository, CatService catService) {
        this.conversationService = conversationService;
        this.conversationRepository = conversationRepository;
        this.catRepository = catRepository;
        this.catService = catService;
    }

    // Create conversation request with userId
    @PostMapping("/conversations")
    public ResponseEntity<String> createNewConversation(@RequestBody CreateConversationRequest request) {

        return ResponseEntity.ok(conversationService.createNewConversation(request));

    }

    // Get All conversations
    @GetMapping("/conversations")
    public List<Conversation> getAllConversations() {
        return conversationService.getAllConversation();
    }
//
//    @GetMapping("/conversations/{catId}")
//    public List<Conversation> getConversationsWithCatId(@PathVariable String conversationId) {
//        return conversationService.getConversationsUserHas(conversationId);
//    }
//

    @DeleteMapping("/conversations/{conversationId}")
    public ResponseEntity<Void> deleteConversationById(@PathVariable String conversationId) {
        conversationService.deleteById(conversationId);
        return ResponseEntity.noContent().build(); // 204 No Content

    }

    @CrossOrigin(origins = "*")
    @GetMapping("/conversations/{conversationId}")
    public Conversation getConversationWithId(
            @PathVariable String conversationId
    ) {
        return conversationService.getConversationsWithId(conversationId);

    }

    //todo: adding message to conversation
    // MOVE THIS TO SERVICE
    @PutMapping("/conversations/{conversationId}")
    public Conversation addMessageToExistingConversation(
            @PathVariable String conversationId,
            @RequestBody ChatMessage chatMessage) {
       return conversationService.addMessageToExistingConversationService(conversationId,chatMessage);
    }
}
