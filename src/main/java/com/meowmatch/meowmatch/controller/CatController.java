package com.meowmatch.meowmatch.controller;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.meowmatch.meowmatch.models.Cat;
import com.meowmatch.meowmatch.repository.CatRepository;
import com.meowmatch.meowmatch.service.CatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.InputStream;
import java.util.List;

@RestController
public class CatController {
private final CatService catService;
    private final CatRepository catRepository;
    private final ObjectMapper objectMapper;
    private boolean alreadySeeded = false;

    public CatController(CatService catService, CatRepository catRepository, ObjectMapper objectMapper) {
        this.catService = catService;
        this.catRepository = catRepository;
        this.objectMapper = objectMapper;
    }

    @GetMapping
    public ResponseEntity<List<Cat>> getAllCats(){

        List<Cat>catList = catService.getAllCat();
        return ResponseEntity.ok(catList);
    }

    @PostMapping("/seedCats")
    public ResponseEntity<String> seedCats() {
        try (InputStream inputStream = getClass().getResourceAsStream("/sample_cats.json")) {
            List<Cat> cats = objectMapper.readValue(inputStream, new TypeReference<>() {});
            catRepository.saveAll(cats);
            return ResponseEntity.ok("✅ Seeded cats to DB");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Failed to seed cats: " + e.getMessage());
        }
    }
}
