package com.meowmatch.meowmatch.controller;

import com.meowmatch.meowmatch.models.Cat;
import com.meowmatch.meowmatch.models.conversations.Conversation;
import com.meowmatch.meowmatch.models.match.Match;
import com.meowmatch.meowmatch.repository.CatRepository;
import com.meowmatch.meowmatch.repository.ConversationRepository;
import com.meowmatch.meowmatch.repository.MatchRepository;
import com.meowmatch.meowmatch.service.MatchService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@RestController
@RequestMapping("/matches")
public class MatchController {
    // this will work like messages section we will see whom we match and our conservations

    private final MatchService matchService;

    public MatchController(MatchService matchService) {
        this.matchService = matchService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Match>> getUsersMatches(@PathVariable String userId){
       return ResponseEntity.ok(matchService.getUsersMatch(userId));
    }

    @GetMapping("/admin")
    public List<Match> getAllMatches(){
        return matchService.getAllCatMatches();
    }

}
