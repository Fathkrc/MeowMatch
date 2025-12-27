package com.meowmatch.meowmatch.controller;

import com.meowmatch.meowmatch.models.Cat;
import com.meowmatch.meowmatch.repository.CatRepository;
import com.meowmatch.meowmatch.service.CatService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController()
@RequestMapping("cat/")
public class CatController {
private final CatService catService;


    public CatController(CatService catService) {
        this.catService = catService;

    }

    @GetMapping
    public ResponseEntity<List<Cat>> getAllCats(){
        return ResponseEntity.ok(catService.getAllCat());
    }

@GetMapping
@RequestMapping("{catId}")
    public ResponseEntity<Cat> getCatById(@PathVariable String catId){
        return ResponseEntity.ok(catService.findById(catId));
}

}
