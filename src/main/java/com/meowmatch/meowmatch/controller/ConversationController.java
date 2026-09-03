package com.meowmatch.meowmatch.controller;

import com.meowmatch.meowmatch.models.conversations.ChatMessage;
import com.meowmatch.meowmatch.models.conversations.Conversation;
import com.meowmatch.meowmatch.service.ConversationService;
import com.meowmatch.meowmatch.service.CurrentUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("conversations")
public class ConversationController {
    private final ConversationService conversationService;
    private final CurrentUserService currentUserService;

    public ConversationController(ConversationService conversationService, CurrentUserService currentUserService) {
        this.conversationService = conversationService;
        this.currentUserService = currentUserService;
    }

    @GetMapping("/admin")
    public List<Conversation> getAllConversations() {
        return conversationService.getAllConversation();
    }

    @GetMapping("/me")
    public ResponseEntity<List<Conversation>> getMyConversations(Authentication authentication) {
        String userCatId = currentUserService.requireCatId(authentication);
        return ResponseEntity.ok(conversationService.getConversationsForCat(userCatId));
    }

    @GetMapping("/{conversationId}")
    public Conversation getConversationWithId(@PathVariable String conversationId) {
        return conversationService.getConversationsWithId(conversationId);
    }

    @DeleteMapping("/{conversationId}")
    public ResponseEntity<Void> deleteConversationById(@PathVariable String conversationId) {
        conversationService.deleteById(conversationId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{conversationId}")
    public Conversation addMessageToExistingConversation(
            @PathVariable String conversationId,
            @RequestBody ChatMessage chatMessage
    ) {
        return conversationService.addMessageToExistingConversationService(conversationId, chatMessage);
    }
}
