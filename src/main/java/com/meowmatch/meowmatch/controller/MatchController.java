package com.meowmatch.meowmatch.controller;

import com.meowmatch.meowmatch.models.match.Match;
import com.meowmatch.meowmatch.service.CatService;
import com.meowmatch.meowmatch.service.MatchService;
import com.meowmatch.meowmatch.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/matches")
public class MatchController {
    // this will work like messages section we will see whom we match and our conservations

    private final MatchService matchService;
    private final CatService catService;
    private final UserService userService;

    public MatchController(MatchService matchService, CatService catService, UserService userService) {
        this.matchService = matchService;
        this.catService = catService;
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ResponseEntity<List<Match>> getUsersMatches(@PathVariable String userId){
       return ResponseEntity.ok(matchService.getUsersMatch(userId));
    }

    @GetMapping("/me")
    public ResponseEntity<List<Match>> getMyMatches(Authentication authentication) {
        String username = (authentication != null) ? String.valueOf(authentication.getPrincipal()) : null;
        if (username == null || username.isBlank() || "anonymousUser".equals(username)) {
            return ResponseEntity.status(401).build();
        }
        String userId = userService.getMe(username).getId();
        String catId = catService.findByUserId(userId).getId();
        return ResponseEntity.ok(matchService.getUsersMatch(catId));
    }

    @GetMapping("/admin")
    public List<Match> getAllMatches(){
        return matchService.getAllCatMatches();
    }

}
