package com.meowmatch.meowmatch.models;
import com.meowmatch.meowmatch.models.enums.Gender;
public record Cat(

        String id,
        String name,
        int age,
        Gender gender,
        String location,
        String imageUrl,
        String breed,
        String bio

) {
}
