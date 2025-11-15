package com.meowmatch.meowmatch.models.dto;

public record CreateConversationRequest(
        String userId,// user that signed in
        String targetedProfileId // user Id we want to match
) {
}
