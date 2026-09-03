package com.meowmatch.meowmatch.models;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.HashSet;
import java.util.Set;

@Document("swipe_state")
public class SwipeState {

    @Id
    private String userCatId;

    private Set<String> likedCatIds = new HashSet<>();
    private Set<String> dislikedCatIds = new HashSet<>();
    private Set<String> matchedIds = new HashSet<>();

    public SwipeState() {}

    public SwipeState(String userCatId) {
        this.userCatId = userCatId;
    }

    public String getUserCatId() {
        return userCatId;
    }

    public Set<String> getLikedCats() {
        return likedCatIds;
    }

    public Set<String> getdislikedCats() {
        return dislikedCatIds;
    }

    public void like(String likedCatId) {
        likedCatIds.add(likedCatId);
    }

    public void dislike(String dislikedCatId) {
        dislikedCatIds.add(dislikedCatId);
    }

    public Set<String> getMatches() {
        return matchedIds;
    }
}
