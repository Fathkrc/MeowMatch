package com.meowmatch.meowmatch.models.match;

import org.springframework.data.annotation.Id;

public class Match {
    @Id
    private String Id;
    private String userCatId;
    private String targetCatId;
    private String conversationId;

    public String getId() {
        return Id;
    }

    public void setId(String id) {
        Id = id;
    }

    public String getUserCatId() {
        return userCatId;
    }

    public void setUserCatId(String userCatId) {
        this.userCatId = userCatId;
    }

    public String getTargetCatId() {
        return targetCatId;
    }

    public void setTargetCatId(String targetCatId) {
        this.targetCatId = targetCatId;
    }

    public String getConversationId() {
        return conversationId;
    }

    public void setConversationId(String conversationId) {
        this.conversationId = conversationId;
    }

    public Match(String userCatId, String targetCatId, String conversationId) {
        this.userCatId = userCatId;
        this.targetCatId = targetCatId;
        this.conversationId = conversationId;
    }
}
