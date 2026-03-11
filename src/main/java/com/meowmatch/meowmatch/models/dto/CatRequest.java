package com.meowmatch.meowmatch.models.dto;

import com.meowmatch.meowmatch.models.enums.Gender;

public record CatRequest(
        String userId,
        String name,
        int age,
        Gender gender,
        String location,
        String imageUrl,
        String breed,
        String bio
) {

}