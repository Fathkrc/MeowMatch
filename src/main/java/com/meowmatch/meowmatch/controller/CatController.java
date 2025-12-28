package com.meowmatch.meowmatch.controller;

import com.meowmatch.meowmatch.models.Cat;
import com.meowmatch.meowmatch.models.dto.CatRequest;
import com.meowmatch.meowmatch.service.CatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("cat")
public class CatController {
    private final CatService catService;


    public CatController(CatService catService) {
        this.catService = catService;

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

    @PostMapping("/newCat")
    public ResponseEntity<Cat> createNewProfile(@RequestBody CatRequest catRequest) {
        Cat cat = catService.createNewCat(catRequest);
        return ResponseEntity.ok(cat);
    }

}
