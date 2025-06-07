package com.meowmatch.meowmatch.service;

import com.meowmatch.meowmatch.repository.CatRepository;
import org.springframework.stereotype.Service;

@Service
public class CatService {
private final CatRepository catRepository;

    public CatService(CatRepository catRepository) {
        this.catRepository = catRepository;
    }
}
