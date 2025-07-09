package com.meowmatch.meowmatch.controller;

import com.meowmatch.meowmatch.models.Cat;
import com.meowmatch.meowmatch.service.CatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
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

        List<Cat>catList = catService.getAllCat();
        return ResponseEntity.ok(catList);
    }

}
