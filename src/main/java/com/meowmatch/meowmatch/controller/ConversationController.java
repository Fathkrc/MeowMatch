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

@RestController()
@RequestMapping("conversations/")

public class ConversationController {
    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;

    }

    // Create conversation request with userId
    @PostMapping()
    public ResponseEntity<String> createNewConversation(@RequestBody CreateConversationRequest request) {

        return ResponseEntity.ok(conversationService.createNewConversation(request));

    }

    // Get All conversations
    @GetMapping()// admin
    @RequestMapping("admin")
    public List<Conversation> getAllConversations() {
        return conversationService.getAllConversation();
    }

    @GetMapping("cat/{catId}")
    public List<Conversation> getConversationsWithCatId(@PathVariable String conversationId) {
        return conversationService.getConversationsUserHas(conversationId);
    }

    @GetMapping("{conversationId}")
    public Conversation getConversationWithId(
            @PathVariable String conversationId
    ) {
        return conversationService.getConversationsWithId(conversationId);

    }

    @DeleteMapping("{conversationId}")
    public ResponseEntity<Void> deleteConversationById(@PathVariable String conversationId) {
        conversationService.deleteById(conversationId);
        return ResponseEntity.noContent().build(); // 204 No Content

    }

    @PutMapping("{conversationId}")
    public Conversation addMessageToExistingConversation(

            @PathVariable String conversationId,
            @RequestBody ChatMessage chatMessage) {
        return conversationService.addMessageToExistingConversationService(conversationId, chatMessage);
    }
}
