package com.meowmatch.meowmatch.controller;

import com.meowmatch.meowmatch.models.conversations.ChatMessage;
import com.meowmatch.meowmatch.models.conversations.Conversation;
import com.meowmatch.meowmatch.models.dto.CreateConversationRequest;
import com.meowmatch.meowmatch.service.CatService;
import com.meowmatch.meowmatch.service.ConversationService;
import com.meowmatch.meowmatch.service.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;

@RestController()
@RequestMapping("conversations")

public class ConversationController {
    private final ConversationService conversationService;
    private final CatService catService;
    private final UserService userService;

    public ConversationController(ConversationService conversationService, CatService catService, UserService userService) {
        this.conversationService = conversationService;
        this.catService = catService;
        this.userService = userService;

    }

    //NOT NECESSARY ANYMORE ,MATCH IS ALREADY CREATING CONVO
//    @PostMapping()
//    public ResponseEntity<String> createNewConversation(@RequestBody CreateConversationRequest request) {
//
//        return ResponseEntity.ok(conversationService.createNewConversation(request));
//
//    }

    // Get All conversations
    @GetMapping("/admin")// admin
    public List<Conversation> getAllConversations() {
        return conversationService.getAllConversation();
    }

// This will be messages section for user
    @GetMapping("/cat/{catId}")
    public List<Conversation> getConversationsWithCatId(@PathVariable String catId) {
        return conversationService.getConversationsUserHas(catId);
    }

    @GetMapping("/me")
    public ResponseEntity<List<Conversation>> getMyConversations(Authentication authentication) {
        String username = (authentication != null) ? String.valueOf(authentication.getPrincipal()) : null;
        if (username == null || username.isBlank() || "anonymousUser".equals(username)) {
            return ResponseEntity.status(401).build();
        }
        String userId = userService.getMe(username).getId();
        String catId = catService.findByUserId(userId).getId();
        return ResponseEntity.ok(conversationService.getConversationsUserHas(catId));
    }

    @GetMapping("/{conversationId}")
    public Conversation getConversationWithId(
            @PathVariable String conversationId
    ) {
        return conversationService.getConversationsWithId(conversationId);

    }

    @DeleteMapping("/{conversationId}")
    public ResponseEntity<Void> deleteConversationById(@PathVariable String conversationId) {
        conversationService.deleteById(conversationId);
        return ResponseEntity.noContent().build(); // 204 No Content

    }
    @PutMapping("/{conversationId}")
    public Conversation addMessageToExistingConversation(

            @PathVariable String conversationId,
            @RequestBody ChatMessage chatMessage) {
        return conversationService.addMessageToExistingConversationService(conversationId, chatMessage);
    }
}
