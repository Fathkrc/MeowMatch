package com.meowmatch.meowmatch.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meowmatch.meowmatch.models.Cat;
import com.meowmatch.meowmatch.repository.CatRepository;
import com.meowmatch.meowmatch.service.CatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.FileReader;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
public class CatController {
private final CatService catService;
    private final CatRepository catRepository;
    private final Gson gson;

    public CatController(CatService catService, CatRepository catRepository, Gson gson) {
        this.catService = catService;
        this.catRepository = catRepository;
        this.gson = gson;
    }

    @GetMapping
    public ResponseEntity<List<Cat>> getAllCats(){

        List<Cat>catList = catService.getAllCat();
        return ResponseEntity.ok(catList);
    }

    @PostMapping("/seedCats")
    public ResponseEntity<String> seedCats() {
        try {
            List<Cat> existingProfiles = gson.fromJson(
                    new FileReader("src/main/java/com/meowmatch/meowmatch/controller/sample_cats1.json"),
                    new TypeToken<ArrayList<Cat>>() {}.getType()
            );
            catRepository.saveAll(existingProfiles);
            return ResponseEntity.ok("✅ Seeded cats to DB");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Failed to seed cats: " + e.getMessage());
        }
    }
}
