package com.meowmatch.meowmatch.models.conversations;

import org.springframework.data.annotation.Id;

import java.util.List;

public class Conversation {
    @Id
    private String id;

    private String profileId;

    private String matchedProfileId;// one cat we match

    private List<ChatMessage> messages;

    public Conversation(String profileId, String matchedProfileId, List<ChatMessage> messages) {
        this.profileId = profileId;
        this.matchedProfileId = matchedProfileId;
        this.messages = messages;
    }

    public String getId() {
        return id;
    }


    public String getProfileId() {
        return profileId;
    }

    public void setProfileId(String profileId) {
        this.profileId = profileId;
    }

    public String getMatchedProfileId() {
        return matchedProfileId;
    }

    public void setMatchedProfileId(String matchedProfileId) {
        this.matchedProfileId = matchedProfileId;
    }

    public List<ChatMessage> getMessages() {
        return messages;
    }

    public void setMessages(List<ChatMessage> messages) {
        this.messages = messages;
    }
}
