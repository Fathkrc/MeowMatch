package com.meowmatch.meowmatch.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meowmatch.meowmatch.models.Cat;
import com.meowmatch.meowmatch.models.enums.Gender;
import com.meowmatch.meowmatch.repository.CatRepository;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

@Service
public class CatService {
private final CatRepository catRepository;
    private final Gson gson;
    private static final String CATS_JSON_FILE_URL= "src/main/resources/sample_cats1.json";

    public CatService(CatRepository catRepository, Gson gson) {
        this.catRepository = catRepository;
        this.gson = gson;
    }

    public List<Cat> getAllCat() {
       return catRepository.findAll();
    }

    public ResponseEntity<String> seedAllCatsFromJsonFile() {
        // Hard coded my lovely lovemaker cat
        Cat furkir=new Cat("a","Furkir",4, Gender.MALE,"Isparta","","Persian","Lovely and playful, never harm any creatures exept his toys");
        try {
            List<Cat> existingProfiles = gson.fromJson(
                    new FileReader(CATS_JSON_FILE_URL),
                    new TypeToken<ArrayList<Cat>>() {}.getType()
            );
            existingProfiles.add(furkir);
            catRepository.saveAll(existingProfiles);
            return ResponseEntity.ok("✅ Seeded cats to DB");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Failed to seed cats: " + e.getMessage());
        }
    }
}
