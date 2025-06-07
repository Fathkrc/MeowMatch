package com.meowmatch.meowmatch.controller;

import com.meowmatch.meowmatch.service.CatService;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CatController {
private final CatService catService;

    public CatController(CatService catService) {
        this.catService = catService;
    }

}
