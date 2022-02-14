package com.lachlan.kingofthecourt.data.entity;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

@Entity
public class User {

    @PrimaryKey
    @NonNull
    private String userId;

    private String firstName;

    private String lastName;

    private String email;

    private String position;

    private int wins;

    private int losses;

    public User() {
        userId = "";
        firstName = "";
        lastName = "";
        email = "";
        position = "";
        wins = 0;
        losses = 0;
    }

    public User(String userId) {
        this.userId = userId;
        firstName = "";
        lastName = "";
        email = "";
        position = "";
        wins = 0;
        losses = 0;
    }

    public User(String userId, String firstName, String lastName, String email, String position) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.position = position;
        wins = 0;
        losses = 0;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getLosses() {
        return losses;
    }

    public void setLosses(int losses) {
        this.losses = losses;
    }
}
