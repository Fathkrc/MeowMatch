package com.meowmatch.meowmatch.models.dto;

public class UserResponse {
    private String id;
    private String username;

    public UserResponse() {}

    public UserResponse(String id, String username) {
        this.id = id;
        this.username = username;
    }

    public String getId() {
        return id;
    }

    public String getUsername() {
        return username;
    }
}