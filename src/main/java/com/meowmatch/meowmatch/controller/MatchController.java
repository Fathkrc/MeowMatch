package com.meowmatch.meowmatch.controller;

import com.meowmatch.meowmatch.models.match.Match;
import com.meowmatch.meowmatch.service.CurrentUserService;
import com.meowmatch.meowmatch.service.MatchService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/matches")
public class MatchController {

    private final MatchService matchService;
    private final CurrentUserService currentUserService;

    public MatchController(MatchService matchService, CurrentUserService currentUserService) {
        this.matchService = matchService;
        this.currentUserService = currentUserService;
    }

    @GetMapping("/me")
    public ResponseEntity<List<Match>> getMyMatches(Authentication authentication) {
        String userCatId = currentUserService.requireCatId(authentication);
        return ResponseEntity.ok(matchService.getMatchesForCat(userCatId));
    }

    @GetMapping("/admin")
    public List<Match> getAllMatches() {
        return matchService.getAllCatMatches();
    }
}
