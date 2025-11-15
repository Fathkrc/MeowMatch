package com.meowmatch.meowmatch.models;


import com.meowmatch.meowmatch.models.match.Match;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.sql.Struct;
import java.util.*;

@Document("swipe_state")
public class SwipeState {

    @Id
    private String userCatId; // same as Cat.id(), one doc per user

    private Set<String> likedCatIds =new HashSet<>();
    private Set<String> dislikedCatIds =new HashSet<>();



    // key: targetCatId, value: conversationId
    private List<Match> matches = new ArrayList<>();

    public SwipeState(String userCatId) {
        this.userCatId = userCatId;
    }

    public String getUserCatId() {
        return userCatId;
    }

    public Set<String> getLikedCats(){
        return likedCatIds;
    }

    public Set<String> getdislikedCats(){
        return dislikedCatIds;
    }
    public void like(Cat likedCat) {
        likedCatIds.add(likedCat.id());
    }

    public void dislike(Cat dislikedCat) {
        dislikedCatIds.add(dislikedCat.id());
    }
//todo: should add match be here ??
    public void addMatch(Match match) {
        matches.add(match);
    }

}
