package com.meowmatch.meowmatch.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meowmatch.meowmatch.models.Cat;
import com.meowmatch.meowmatch.models.dto.CatRequest;
import com.meowmatch.meowmatch.models.enums.Gender;
import com.meowmatch.meowmatch.repository.CatRepository;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.io.FileReader;
import java.util.*;

@Service
public class CatService {
private final CatRepository catRepository;
    private final Gson gson;
    private static final String CATS_JSON_FILE_URL= "src/main/resources/sample_cats1.json";

    public CatService(CatRepository catRepository, Gson gson) {
        this.catRepository = catRepository;
        this.gson = gson;
    }

    public List<Cat> findAll() {
       return catRepository.findAll();
    }

    public ResponseEntity<String> seedAllCatsFromJsonFile() {
        // Hard coded my lovely lovemaker cat
//        Cat furkir=new Cat("Furkir",4, Gender.MALE,"Isparta","","Persian","Lovely and playful, never harm any creatures except his toys");
        try {
            List<Cat> existingProfiles = gson.fromJson(
                    new FileReader(CATS_JSON_FILE_URL),
                    new TypeToken<ArrayList<Cat>>() {}.getType()
            );
//            existingProfiles.add(furkir);
            System.out.print("cats seed");
            catRepository.saveAll(existingProfiles);
            return ResponseEntity.ok("✅ Seeded cats to DB");
        } catch (Exception e) {
            return ResponseEntity.status(500).body("❌ Failed to seed cats: " + e.getMessage());
        }
    }


    public Cat findById(String requestedCatId) {
        return catRepository.findById(requestedCatId).orElseThrow(()->new ResponseStatusException(HttpStatus.NO_CONTENT,"user Not Found with " + requestedCatId+" id"));
    }

    public Cat updateExistingCat(String catId, CatRequest catRequest) {
        Cat cat=findById(catId);
        Cat newCat=new Cat(
                catRequest.name(),
                catRequest.age(),
                catRequest.gender(),
                catRequest.location(),
                catRequest.imageUrl(),
                catRequest.breed(),
                catRequest.bio()

        );
        catRepository.deleteById(catId);
        catRepository.save(newCat);
        return newCat;
    }

    public Cat createNewCat(CatRequest catRequest) {
        Cat newCat= new Cat(
                catRequest.name(),
                catRequest.age(),
                catRequest.gender(),
                catRequest.location(),
                catRequest.imageUrl(),
                catRequest.breed(),
                catRequest.bio()

        );
        catRepository.save(newCat);
        return newCat;
    }
}
