package com.meowmatch.meowmatch.models;


import com.meowmatch.meowmatch.models.match.Match;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Document("swipe_state")
public class SwipeState {

    @Id
    private String userCatId; // same as Cat.id(), one doc per user

    private Set<Cat> likedCats ;
    private Set<Cat> dislikedCats ;



    // key: targetCatId, value: conversationId
    private List<Match> matches = new ArrayList<>();

    public SwipeState(String userCatId, Set<Cat> likedCats,Set<Cat> dislikedCats) {
        this.userCatId = userCatId;
        this.likedCats = likedCats;
        this.dislikedCats=dislikedCats;
    }

    public String getUserCatId() {
        return userCatId;
    }


    public void like(Cat likedCat) {
        likedCats.add(likedCat);
    }

    public void dislike(Cat dislikedCat) {
        dislikedCats.add(dislikedCat);
    }
//todo: should add match be here ??
    public void addMatch(Match match) {
        matches.add(match);
    }

}
