package com.meowmatch.meowmatch.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meowmatch.meowmatch.models.Cat;
import com.meowmatch.meowmatch.models.dto.CatRequest;
import com.meowmatch.meowmatch.repository.CatRepository;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CatService {

    private final CatRepository catRepository;
    private final Gson gson;
    private static final String CATS_JSON_FILE_URL = "src/main/resources/sample_cats1.json";

    public CatService(CatRepository catRepository, Gson gson) {
        this.catRepository = catRepository;
        this.gson = gson;
    }

    public List<Cat> findAll() {
        return catRepository.findAll();
    }

    public ResponseEntity<String> seedAllCatsFromJsonFile() {
        try {
            List<Cat> existingProfiles = gson.fromJson(
                    new FileReader(CATS_JSON_FILE_URL),
                    new TypeToken<ArrayList<Cat>>() {}.getType()
            );

            catRepository.saveAll(existingProfiles);
            return ResponseEntity.ok("✅ Seeded cats to DB");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Failed to seed cats: " + e.getMessage());
        }
    }

    public Cat findById(String requestedCatId) {
        return catRepository.findById(requestedCatId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cat not found with id " + requestedCatId
                ));
    }

    public Cat findByUserId(String userId) {
        return catRepository.findByUserId(userId)
                .orElseThrow(() -> new ResponseStatusException(
                        HttpStatus.NOT_FOUND,
                        "Cat not found for user id " + userId
                ));
    }

    public Cat updateExistingCat(String catId, CatRequest catRequest) {
        Cat cat = findById(catId);

        cat.setName(catRequest.name());
        cat.setAge(catRequest.age());
        cat.setGender(catRequest.gender());
        cat.setLocation(catRequest.location());
        cat.setImageUrl(catRequest.imageUrl());
        cat.setBreed(catRequest.breed());
        cat.setBio(catRequest.bio());

        return catRepository.save(cat);
    }

    public Cat createNewCat(String userId, CatRequest catRequest) {
        if (catRepository.existsByUserId(userId)) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "This user already has a cat profile"
            );
        }

        Cat newCat = new Cat();
        newCat.setUserId(userId);
        newCat.setName(catRequest.name());
        newCat.setAge(catRequest.age());
        newCat.setGender(catRequest.gender());
        newCat.setLocation(catRequest.location());
        newCat.setImageUrl(catRequest.imageUrl());
        newCat.setBreed(catRequest.breed());
        newCat.setBio(catRequest.bio());

        return catRepository.save(newCat);
    }
}