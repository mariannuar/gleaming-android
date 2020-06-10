package com.example.gleaming.model;

public class UserSingleResponse {

    private User user;

    public UserSingleResponse() {
    }

    public UserSingleResponse(User user) {
        this.user = user;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }


    @Override
    public String toString() {
        return "UserSingleResponse{" +
                "user=" + user +
                '}';
    }
}