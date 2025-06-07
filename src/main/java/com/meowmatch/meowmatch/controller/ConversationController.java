package com.meowmatch.meowmatch.controller;

import com.meowmatch.meowmatch.models.conversations.Conversation;
import com.meowmatch.meowmatch.models.dto.CreateConversationRequest;
import com.meowmatch.meowmatch.service.ConversationService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ConversationController {
    private final ConversationService conversationService;

    public ConversationController(ConversationService conversationService) {
        this.conversationService = conversationService;
    }

    @PostMapping("/conversations")
    public ResponseEntity<String> createNewConversation(@RequestBody CreateConversationRequest request){

        return ResponseEntity.ok(conversationService.createNewConversation(request));

    }
    @GetMapping("/conversations")
    public List<Conversation> getAllConversations(){
        return conversationService.getAllConversation();
    }
    @DeleteMapping("/conversations")
    public ResponseEntity<Void> deleteConversationById(@RequestBody String id){
        conversationService.deleteById(id);
        return ResponseEntity.noContent().build(); // 204 No Content

    }
}
