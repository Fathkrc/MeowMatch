package com.meowmatch.meowmatch.models.conversations;

import java.time.LocalDateTime;

public record ChatMessage(
        String messageText,
        String authorId,
        String targetedProfile,
        LocalDateTime messageTime

) {
}
