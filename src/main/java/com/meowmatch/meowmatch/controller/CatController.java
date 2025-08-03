package com.meowmatch.meowmatch.controller;

import com.meowmatch.meowmatch.models.Cat;
import com.meowmatch.meowmatch.repository.CatRepository;
import com.meowmatch.meowmatch.service.CatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.List;

@RestController
public class CatController {
private final CatService catService;


    public CatController(CatService catService) {
        this.catService = catService;

    }

    @GetMapping
    public ResponseEntity<List<Cat>> getAllCats(){
        return ResponseEntity.ok(catService.getAllCat());
    }

    @PostMapping("/seedCats")
    public ResponseEntity<String> seedCatsToDb() {
        return catService.seedAllCatsFromJsonFile();
    }
}
