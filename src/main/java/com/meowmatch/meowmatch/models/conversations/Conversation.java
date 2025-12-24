package com.meowmatch.meowmatch.models.conversations;

import com.meowmatch.meowmatch.models.Cat;

import java.util.List;

public record Conversation(
        String id,

        String profileId,

        String matchedProfileId,// one cat we match

        List<ChatMessage> messages

) {

}
