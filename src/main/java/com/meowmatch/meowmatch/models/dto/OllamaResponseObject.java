package com.meowmatch.meowmatch.models.dto;

import java.util.List;

public record OllamaResponseObject(
        String model,
        String response,
        boolean done,
        String done_reason,

        // Optional: if you want more fields like total_duration, context, etc.
        long total_duration,
        List<Integer> context

) {

}
