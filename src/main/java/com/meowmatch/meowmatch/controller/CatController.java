package com.meowmatch.meowmatch.controller;

import com.meowmatch.meowmatch.models.Cat;
import com.meowmatch.meowmatch.models.dto.CatRequest;
import com.meowmatch.meowmatch.models.dto.UserResponse;
import com.meowmatch.meowmatch.service.CatService;
import com.meowmatch.meowmatch.service.UserService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("cat")
public class CatController {
    private final CatService catService;
    private final UserService userService;


    public CatController(CatService catService, UserService userService) {
        this.catService = catService;
        this.userService = userService;

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
    public ResponseEntity<Cat> updateById(@PathVariable String catId, @RequestBody CatRequest catrequest) {
        return ResponseEntity.ok(catService.updateExistingCat(catId, catrequest));
    }

    @GetMapping("/me")
    public ResponseEntity<Cat> getMyCat(Authentication authentication) {
        String username = (authentication != null) ? String.valueOf(authentication.getPrincipal()) : null;
        if (username == null || username.isBlank() || "anonymousUser".equals(username)) {
            return ResponseEntity.status(401).build();
        }
        UserResponse me = userService.getMe(username);
        return ResponseEntity.ok(catService.findByUserId(me.getId()));
    }

    @PostMapping("/newCat")
    public ResponseEntity<Cat> createNewProfile(Authentication authentication, @RequestBody CatRequest catRequest) {
        String username = (authentication != null) ? String.valueOf(authentication.getPrincipal()) : null;
        if (username == null || username.isBlank() || "anonymousUser".equals(username)) {
            return ResponseEntity.status(401).build();
        }
        UserResponse me = userService.getMe(username);
        Cat cat = catService.createNewCat(me.getId(), catRequest);
        return ResponseEntity.ok(cat);
    }

}
