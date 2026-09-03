package com.meowmatch.meowmatch.controller;

import com.meowmatch.meowmatch.models.Cat;
import com.meowmatch.meowmatch.models.dto.CatRequest;
import com.meowmatch.meowmatch.service.CatService;
import com.meowmatch.meowmatch.service.CurrentUserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("cat")
public class CatController {
    private final CatService catService;
    private final CurrentUserService currentUserService;

    public CatController(CatService catService, CurrentUserService currentUserService) {
        this.catService = catService;
        this.currentUserService = currentUserService;
    }

    @GetMapping("/allProfiles")
    public ResponseEntity<List<Cat>> getAllCats() {
        return ResponseEntity.ok(catService.findAll());
    }

    @GetMapping("/{catId}")
    public ResponseEntity<Cat> getCatById(@PathVariable String catId) {
        return ResponseEntity.ok(catService.findById(catId));
    }

    @PutMapping("/{catId}")
    public ResponseEntity<Cat> updateById(@PathVariable String catId, @RequestBody CatRequest catRequest) {
        return ResponseEntity.ok(catService.updateExistingCat(catId, catRequest));
    }

    @GetMapping("/me")
    public ResponseEntity<Cat> getMyCat(Authentication authentication) {
        return ResponseEntity.ok(currentUserService.requireCat(authentication));
    }

    @PostMapping("/newCat")
    public ResponseEntity<Cat> createNewProfile(Authentication authentication, @RequestBody CatRequest catRequest) {
        String accountUserId = currentUserService.requireUser(authentication).getId();
        Cat cat = catService.createNewCat(accountUserId, catRequest);
        return ResponseEntity.ok(cat);
    }
}
